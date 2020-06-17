package tech.gruppone.stalker.server.services;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.InvalidLdapCredentialsException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.exceptions.UnexpectedErrorException;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Log4j2
public class ConnectionService {
  ConnectionRepository connectionRepository;

  LdapConfigurationRepository ldapConfigurationRepository;
  OrganizationRepository organizationRepository;

  private Mono<Void> createConnection(final long userId, final long organizationId) {

    final ConnectionDao connectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    return connectionRepository
        .save(connectionDao)
        .onErrorMap(
            DataIntegrityViolationException.class,
            error -> {
              log.error(error.getMessage());

              return new BadRequestException();
            })
        .then();
  }

  public Mono<Void> forceCreateConnection(final long userId, final long organizationId) {
    return createConnection(userId, organizationId);
  }

  public Mono<Void> createPublicConnection(final long userId, final long organizationId) {

    return organizationRepository
        .findById(organizationId)
        .map(OrganizationDao::getOrganizationType)
        .filter(organizationType -> organizationType.equals("public"))
        .switchIfEmpty(Mono.error(BadRequestException::new))
        // only create connection if organization is actually public
        .flatMap((value) -> createConnection(userId, organizationId))
        .then();
  }

  public Mono<Void> createPrivateConnection(
      final String ldapUserCn,
      final String ldapUserPassword,
      final long userId,
      final long organizationId) {

    return ldapConfigurationRepository
        .findByOrganizationId(organizationId)
        // could not find a suitable ldap configuration
        .switchIfEmpty(Mono.error(BadRequestException::new))
        .flatMap(
            ldapConfiguration -> {
              final var url = ldapConfiguration.getUrl();
              final var baseDn = ldapConfiguration.getBaseDn();
              final var bindRdn = ldapConfiguration.getBindRdn();
              final var bindPassword = ldapConfiguration.getBindPassword();

              // the try with resources ensures the connection is properly closed.
              try (final LdapConnection connection = new LdapNetworkConnection(url, 389)) {

                connection.bind(bindRdn + "," + baseDn, bindPassword);

                final EntryCursor cursor =
                    connection.search(
                        baseDn, String.format("(cn=%s)", ldapUserCn), SearchScope.SUBTREE);

                boolean existsCn = false;
                boolean existsPassword = false;

                for (final var entry : cursor) {
                  if (entry.getAttributes().stream()
                      .anyMatch(e -> e.get().getString().equals(ldapUserPassword))) {
                    existsPassword = true;
                  }

                  existsCn = true;
                }

                if (!(existsCn && existsPassword)) {
                  // connection.close();

                  throw new InvalidLdapCredentialsException();
                }

                cursor.close();
                connection.unBind();

                log.info(
                    "Private user connection created for user {} into the organization {}",
                    userId,
                    organizationId);
              } catch (final LdapException e) {
                log.error(
                    e
                        + "\nFailed to create private user connection for {}: the LDAP configuration is not valid.",
                    userId);

                throw new BadRequestException();
              } catch (final IOException e) {
                log.error(e);
                throw new UnexpectedErrorException();
              }

              log.info(
                  "no errors encountered while validating user with id {} and cn {}",
                  userId,
                  ldapUserCn);

              return createConnection(userId, organizationId);
            })
        .then();
  }

  public Mono<Void> deleteConnection(final long userId, final long organizationId) {

    return connectionRepository
        .deleteByUserIdAndOrganizationId(userId, organizationId)
        .filter(rowsUpdated -> rowsUpdated == 1)
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .then();
  }
}

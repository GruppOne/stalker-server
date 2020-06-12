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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.ConnectionController;
import tech.gruppone.stalker.server.exceptions.BadPrivateConnectionException;
import tech.gruppone.stalker.server.exceptions.BadPublicConnectionException;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.InvalidLdapCredentialsException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.exceptions.UnexpectedErrorException;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Log4j2
public class ConnectionService {
  ConnectionRepository connectionRepository;
  OrganizationRepository organizationRepository;
  LdapConfigurationRepository ldapConfigurationRepository;

  public Mono<Void> createPublicUserConnection(
      ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap,
      long userId,
      long organizationId) {

    return organizationRepository
        .findById(organizationId)
        .filter(c -> c.getOrganizationType().equals("public"))
        .switchIfEmpty(Mono.error(BadPublicConnectionException::new))
        .flatMap(
            o -> {
              log.info(
                  "Public user connection created for user {} into the organization {}",
                  userId,
                  organizationId);
              return connectionRepository.save(
                  ConnectionDao.builder().userId(userId).organizationId(organizationId).build());
            })
        .then();
  }

  public Mono<Void> createPrivateUserConnection(
      ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap,
      long userId,
      long organizationId) {

    return organizationRepository
        .findById(organizationId)
        .filter(o -> o.getOrganizationType().equals("private"))
        .switchIfEmpty(Mono.error(BadPrivateConnectionException::new))
        .flatMap(
            l ->
                ldapConfigurationRepository
                    .findById(l.getId())
                    .filter(f -> f.getOrganizationId() != null)
                    .switchIfEmpty(Mono.error(NotFoundException::new))
                    .flatMap(
                        c -> {
                          if (c.getUsername().equals(ldap.getLdapUsername())
                              && c.getPassword().equals(ldap.getLdapPassword())) {
                            try {
                              LdapConnection connection =
                                  new LdapNetworkConnection(c.getUrl(), 389);
                              // TODO find a way to hide the admin credentials (the only ones which
                              // permits to open a connection)
                              connection.bind("cn=admin,dc=stalker,dc=intern", "adminPassword");

                              EntryCursor cursor =
                                  connection.search(
                                      "dc=stalker,dc=intern",
                                      "(uid=" + ldap.getLdapUsername() + ")",
                                      SearchScope.SUBTREE);

                              boolean existsUsername = false;
                              boolean existsPassword = false;
                              for (var entry : cursor) {
                                if (entry.getAttributes().stream()
                                    .anyMatch(
                                        e -> e.get().getString().equals(ldap.getLdapPassword())))
                                  existsPassword = true;

                                existsUsername = true;
                              }
                              if (!existsUsername | !existsPassword)
                                throw new InvalidLdapCredentialsException();

                              try {
                                cursor.close();
                              } catch (IOException e) {
                                throw new UnexpectedErrorException();
                              }

                              connection.unBind();

                              log.info(
                                  "Private user connection created for user {} into the organization {}",
                                  userId,
                                  organizationId);
                            } catch (LdapException e) {
                              log.info(
                                  "Fail to create private user connection for {}: the LDAP credentials saved are not valid.",
                                  userId);
                              throw new BadRequestException();
                            }

                            return connectionRepository.save(
                                ConnectionDao.builder()
                                    .userId(userId)
                                    .organizationId(organizationId)
                                    .build());
                          } else throw new InvalidLdapCredentialsException();
                        }))
        .then();
  }

  public Mono<Void> deleteUserConnection(long userId, long organizationId) {

    return connectionRepository
        .findConnectionByUserIdAndOrganizationId(userId, organizationId)
        .filter(c -> c.getUserId().equals(userId) && c.getOrganizationId().equals(organizationId))
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .flatMap(
            o -> {
              log.info("User {} exited now by the organization {}", userId, organizationId);
              return connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId);
            });
  }
}

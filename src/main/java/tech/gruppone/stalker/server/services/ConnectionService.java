package tech.gruppone.stalker.server.services;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.ConnectionController;
import tech.gruppone.stalker.server.exceptions.ForbiddenException;
import tech.gruppone.stalker.server.model.api.LdapConfigurationDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Log4j2
public class ConnectionService {
  ConnectionRepository connectionRepository;
  OrganizationRepository organizationRepository;

  public Mono<Void> createUserConnection(
    ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap, long userId, long organizationId) {

    organizationRepository
        .findById(organizationId)
        .subscribe(
            c -> {
              if (c.getOrganizationType().contains("public")) {
                log.info("creating public user connection for {}", userId);
              } else {
                log.info("get credentials from db ");
                connectionRepository
                    .getLdapById(organizationId)
                    .subscribe(
                        c1 -> {
                          log.info("checking credentials for {}", organizationId);
                          if (c1.getBindPassword().equals(ldap.getLdapPassword())
                              && c1.getBindDn().equals(ldap.getRdn())) {
                            log.info("creating private user connection for {}", userId);
                            try (LdapConnection connection =
                                new LdapNetworkConnection(c1.getUrl(), 389)) {
                              connection.bind(ldap.getRdn(), ldap.getLdapPassword());
                              connectionRepository.createUserConnection(userId, organizationId);
                            } catch (LdapException e) {
                              log.info("fail to create private user connection for {}", userId);
                            } catch (IOException e) {
                              log.info("fail to create private user connection for {}", userId);
                            }

                          } else {
                            log.info("wrong password or username for {}", organizationId);
                            throw new ForbiddenException();
                          }
                        });
              }
            });

    return connectionRepository.createUserConnection(userId, organizationId);
  }

  public Mono<Void> deleteUserConnection(long userId, long organizationId) {

    Mono<OrganizationDao> organization = organizationRepository.findById(organizationId);
    if (organization.block().getOrganizationType().equals("private")) {

      try (LdapConnection connection = new LdapNetworkConnection("localhost", 389)) {
        connection.unBind();
      } catch (LdapException e) {
      } catch (IOException e) {
      } finally {
      }
    }

    return connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId).then();
  }
}

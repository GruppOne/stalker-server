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
import tech.gruppone.stalker.server.exceptions.ForbiddenException;
import tech.gruppone.stalker.server.model.api.LdapConfigurationDto;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;
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
      LdapConfigurationDto ldap, long userId, long organizationId) {

    Mono<OrganizationDao> organization = organizationRepository.findById(organizationId);
    if (organization.block().getIsPrivate()) {

      LdapConfigurationDao trueLdap = connectionRepository.getLdapById(organizationId).block();

      if (!(trueLdap.getPassword().equals(ldap.getPassword())
          && trueLdap.getUsername().equals(ldap.getUsername()))) {
        return Mono.error(ForbiddenException::new);
      }
      try (LdapConnection connection = new LdapNetworkConnection("localhost", 389)) {
        connection.bind(ldap.getUsername(), ldap.getPassword());
      } catch (LdapException e) {
      } catch (IOException e) {
      } finally {
      }
    }

    return connectionRepository.createUserConnection(userId, organizationId);
  }
}

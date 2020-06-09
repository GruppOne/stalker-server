package tech.gruppone.stalker.server.services;

import io.micrometer.core.lang.NonNull;
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
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.InvalidLdapCredentialsException;
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

  // NEEDED FOR ENCRYPT PASSWORD --> ONLY IF THIS OPERATION IS SERVER-SIDE
  // private static String hashPassword(final String password) {

  //   MessageDigest md;

  //   try {
  //     md = MessageDigest.getInstance("SHA-512");
  //   } catch (final NoSuchAlgorithmException e) {
  //     throw new UnsupportedOperationException("this should never happen");
  //   }

  //   final byte[] messageDigest = md.digest(password.getBytes());
  //   final StringBuilder stringBuilder = new StringBuilder();

  //   for (int i = 0; i < messageDigest.length; i++) {
  //     stringBuilder.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
  //   }

  //   return stringBuilder.toString();
  // }

  public Mono<Void> createUserConnection(
      ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap,
      @NonNull long userId,
      @NonNull long organizationId) {

    return Mono.just(
            organizationRepository
                .findById(organizationId)
                .subscribe(
                    c -> {
                      if (c.getOrganizationType().equals("public")) {
                        if (ldap != null) throw new BadRequestException();
                        else connectionRepository.createUserConnection(userId, organizationId);
                      } else if (c.getOrganizationType().equals("private")) {
                        if (ldap == null) throw new BadRequestException();
                        else {
                          connectionRepository
                              .getLdapById(organizationId)
                              .subscribe(
                                  c1 -> {
                                    if (c1.getBindDn().equals(ldap.getRdn())
                                        && c1.getBindPassword().equals(ldap.getLdapPassword())) {
                                      try (LdapConnection connection =
                                          new LdapNetworkConnection(c1.getUrl(), 389)) {
                                        connection.bind(ldap.getRdn(), ldap.getLdapPassword());
                                        connectionRepository.createUserConnection(
                                            userId, organizationId);
                                      } catch (LdapException e) {
                                        log.info(
                                            "fail to create private user connection for {}",
                                            userId);
                                      } catch (IOException e) {
                                        log.info(
                                            "fail to create private user connection for {}",
                                            userId);
                                      }
                                    } else throw new InvalidLdapCredentialsException();
                                  });
                        }
                      } else throw new BadRequestException();
                    }))
        .then();
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

package tech.gruppone.stalker.server.services;

import io.micrometer.core.lang.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.directory.api.ldap.model.exception.LdapException;
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

  public Mono<Void> createPublicUserConnection(
      ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap,
      @NonNull long userId,
      @NonNull long organizationId) {

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
              return connectionRepository.createUserConnection(userId, o.getId());
            });
  }

  public Mono<Void> createPrivateUserConnection(
      ConnectionController.PostUserByIdOrganizationByIdConnectionBody ldap,
      @NonNull long userId,
      @NonNull long organizationId) {

    return organizationRepository
        .findById(organizationId)
        .filter(o -> o.getOrganizationType().equals("private"))
        .switchIfEmpty(Mono.error(BadPrivateConnectionException::new))
        .doOnNext(
            o -> {
              connectionRepository
                  .getLdapById(o.getId())
                  .doOnNext(
                      c -> {
                        if (c.getBindDn().equals(ldap.getRdn())
                            && c.getBindPassword().equals(ldap.getLdapPassword())) {
                          try {
                            LdapConnection connection = new LdapNetworkConnection(c.getUrl(), 389);
                            connection.bind(ldap.getRdn(), ldap.getLdapPassword());

                            // TODO searchQuery??

                            log.info(
                                "Private user connection created for user {} into the organization {}",
                                userId,
                                organizationId);
                          } catch (LdapException e) {
                            log.info(
                                "Fail to create private user connection for {}: LDAP authentication doesn't work",
                                userId);
                            throw new BadRequestException();
                          }
                        } else {
                          throw new InvalidLdapCredentialsException();
                        }
                      });
            })
        .flatMap(
            o -> {
              return connectionRepository.createUserConnection(userId, o.getId());
            });
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

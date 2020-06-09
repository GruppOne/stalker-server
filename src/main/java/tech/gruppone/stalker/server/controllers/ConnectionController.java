package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.services.ConnectionService;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}/organization/{organizationId}/connection")
public class ConnectionController {

  ConnectionService connectionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postUserByIdOrganizationByIdConnection(
    @RequestBody PostUserByIdOrganizationByIdConnectionBody ldap,
    @PathVariable("userId") long userId,
    @PathVariable("organizationId") long organizationId) {
      // @PathVariable("userId") final long userId,
      // @PathVariable("organizationId") final long organizationId,
      // @RequestBody(required = false) final PostUserByIdOrganizationByIdConnectionBody requestBody) {

    return connectionService.createUserConnection(ldap, userId, organizationId);

    // if (requestBody != null) {
    //   log.info("connecting to private organization. Request body is: {}", requestBody);
    //   // TODO implement functionality.
    //   // TODO should throw InvalidLdapCredentialsException if given rdn + pw are not valid.
    //   return Mono.error(NotImplementedException::new);
    // }

    // return connectionService.createUserConnection(ldap, userId, organizationId);

    // // FIXME controllers should not know daos
    // final ConnectionDao connectionDao =
    //     ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    // // TODO should throw BadRequestException if connection is already present.
    // return connectionRepository.save(connectionDao).then();
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserByIdOrganizationByIdConnection(
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") final long organizationId) {
    return connectionService.deleteUserConnection(userId, organizationId);
  }

  @Value
  public static class PostUserByIdOrganizationByIdConnectionBody {

    return connectionService.deleteUserConnection(userId, organizationId);
  }
}

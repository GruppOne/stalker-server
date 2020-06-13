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
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.services.ConnectionService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}/organization/{organizationId}/connection")
public class ConnectionController {

  ConnectionService connectionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postUserByIdOrganizationByIdConnection(
      @RequestBody(required = false) final PostUserByIdOrganizationByIdConnectionBody ldap,
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") final long organizationId) {

    if (ldap == null)
      return connectionService.createPublicUserConnection(ldap, userId, organizationId);
    else return connectionService.createPrivateUserConnection(ldap, userId, organizationId);
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

    @NonNull String ldapCn;
    @NonNull String ldapPassword;
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class RolesController {

  @GetMapping("/organization/{organizationId}/users/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getOrganizationByIdUsersRoles(
      @PathVariable("organizationId") final long organizationId) {

    return Mono.error(NotImplementedException::new);
  }

  @GetMapping("/user/{userId}/organizations/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getUserByIdOrganizationsRoles(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.UserRoleInOrganizationDto;
import tech.gruppone.stalker.server.services.RolesService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class RolesController {

  RolesService rolesService;

  @GetMapping("/organization/{organizationId}/users/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getOrganizationByIdUsersRoles(
      @PathVariable("organizationId") final long organizationId) {

    return Mono.error(NotImplementedException::new);
  }

  @GetMapping("/user/{userId}/organizations/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserUserIdOrganizationsRolesResponse> getUserByIdOrganizationsRoles(@PathVariable("userId") final long userId) {

    return rolesService
        .findRoleByUserId(userId)
        .collectList()
        .map(UserUserIdOrganizationsRolesResponse::new);
  }

  @Value
  private static class UserUserIdOrganizationsRolesResponse {
    List<UserRoleInOrganizationDto> rolesInOrganizations;
  }
}

package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserRoleInOrganizationDto;
import tech.gruppone.stalker.server.services.UserRoleService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}/organizations/roles")
public class UserRoleController {
  UserRoleService userRoleService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserUserIdOrganizationsRolesResponse> getUserByIdOrganizationsRoles(
      @PathVariable final Long userId) {

    return userRoleService
        .findRoleByUserId(userId)
        .collectList()
        .map(UserUserIdOrganizationsRolesResponse::new);
  }

  @Value
  private static class UserUserIdOrganizationsRolesResponse {
    List<UserRoleInOrganizationDto> rolesInOrganizations;
  }
}

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
import tech.gruppone.stalker.server.model.api.RolesInOrganizationsDto;
import tech.gruppone.stalker.server.model.api.UsersWithRolesDto;
import tech.gruppone.stalker.server.services.RoleService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class RolesController {

  RoleService roleService;

  @GetMapping("/organization/{organizationId}/users/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UsersWithRolesDto> getOrganizationByIdUsersRoles(
      @PathVariable("organizationId") final long organizationId) {

    return roleService.findUsersWithRolesByOrganizationId(organizationId);
  }

  @GetMapping("/user/{userId}/organizations/roles")
  @ResponseStatus(HttpStatus.OK)
  public Mono<RolesInOrganizationsDto> getUserByIdOrganizationsRoles(
      @PathVariable("userId") final long userId) {

    return roleService.findRolesInOrganizationsByUserId(userId);
  }
}

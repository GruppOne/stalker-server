package tech.gruppone.stalker.server.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;
import tech.gruppone.stalker.server.services.RoleService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/user/{userId}/role")
public class RoleController {
  OrganizationRoleRepository organizationRoleRepository;
  RoleService roleService;

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return organizationRoleRepository
        .deleteByOrganizationIdAndUserId(organizationId, userId)
        .then();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId,
      @RequestBody final RoleRequestBody requestBody) {

    return roleService.create(organizationId, userId, requestBody.getAdministratorType());
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId,
      @RequestBody final RoleRequestBody requestBody) {

    return roleService.update(organizationId, userId, requestBody.getAdministratorType());
  }

  @Value
  static class RoleRequestBody {
    @NonNull
    @JsonAlias({"newRole", "modifiedRole"})
    AdministratorType administratorType;
  }
}

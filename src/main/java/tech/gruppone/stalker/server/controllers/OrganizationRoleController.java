package tech.gruppone.stalker.server.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRole;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/user/{userId}")
public class OrganizationRoleController {

  private final OrganizationRoleRepository organizationRoleRepository;

  @PostMapping("/role")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") long organizationId,
      @PathVariable("userId") long userId,
      @RequestBody RoleRequestBody requestBody) {

    log.info("request body: {}", requestBody);

    OrganizationRole organizationRole =
        OrganizationRole.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(requestBody.getAdministratorType())
            .build();

    log.info(organizationRole);

    return organizationRoleRepository.save(organizationRole).then();
  }

  @Value
  private static class RoleRequestBody {
    @JsonAlias({"newRole", "modifiedRole"})
    @NonNull
    AdministratorType administratorType;
  }
}

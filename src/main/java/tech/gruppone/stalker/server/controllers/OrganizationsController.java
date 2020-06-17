package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.services.ConnectionService;
import tech.gruppone.stalker.server.services.OrganizationService;
import tech.gruppone.stalker.server.services.RoleService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

  OrganizationService organizationService;

  ConnectionService connectionService;
  RoleService roleService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationsResponse> getOrganizations() {
    return organizationService.findAll().collectList().map(GetOrganizationsResponse::new);
  }

  @Value
  private static class GetOrganizationsResponse {
    List<OrganizationDto> organizations;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<PostOrganizationsResponse> postOrganizations(
      @RequestBody final PostOrganizationsRequestBody requestBody) {

    final var ownerId = requestBody.getOwnerId();
    final var organizationDataDto = requestBody.getOrganizationData();

    return organizationService
        .save(organizationDataDto)
        .doOnNext(
            organizationId ->
                connectionService.forceCreateConnection(ownerId, organizationId).subscribe())
        .doOnNext(
            organizationId ->
                roleService.create(organizationId, ownerId, AdministratorType.OWNER).subscribe())
        .map(PostOrganizationsResponse::new);
  }

  @Value
  static class PostOrganizationsRequestBody {

    @NonNull Long ownerId;
    @NonNull OrganizationDataDto organizationData;
  }

  @Value
  static class PostOrganizationsResponse {
    long id;
  }
}

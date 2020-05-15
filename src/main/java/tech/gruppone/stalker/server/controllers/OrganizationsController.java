package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.services.OrganizationService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

  OrganizationService organizationService;

  @GetMapping
  public Mono<GetOrganizationsResponse> getOrganizations() {
    return organizationService.findAll().collectList().map(GetOrganizationsResponse::new);
  }

  @PostMapping
  public Mono<PostOrganizationsResponse> postOrganizations(
      @RequestBody OrganizationDataDto organizationDataDto) {
    return Mono.error(NotImplementedException::new);
  }

  @Value
  private static class GetOrganizationsResponse {
    List<OrganizationDto> organizations;
  }

  @Value
  private static class PostOrganizationsResponse {
    long id;
  }
}

package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@Value
@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

  OrganizationRepository organizationRepository;

  @GetMapping
  public Mono<OrganizationsResponse> getOrganizations() {
    // return organizationRepository.findAll().collectList().map(OrganizationsResponse::new);
    return Mono.error(NotImplementedException::new);
  }

  @Value
  private static class OrganizationsResponse {
    List<OrganizationDto> organizations;
  }
}

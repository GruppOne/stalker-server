package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@RequestMapping("/organizations")
@RestController
// we need the class to be not final in order to enable PreAuthorize annotation
@Data
public class OrganizationController {

  @Getter(AccessLevel.NONE)
  private final OrganizationRepository organizationRepository;


  // TODO refactor this. it needs to return a valid json object: {"organizations":[...]}
  @GetMapping("/all")
  public Flux<Organization> getOrganizations() {

    return organizationRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Organization> getOrganizationById(@PathVariable Long id) {

    return organizationRepository.findById(id);
  }
}

package tech.gruppone.stalker.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@RequestMapping("/organizations")
@RestController
@Value
public class OrganizationController {

  OrganizationRepository organizationRepository;

  // TODO refactor this. it needs to return a valid json object: {"organizations":[...]}
  @GetMapping
  public Flux<Organization> getOrganizations() {

    return organizationRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Organization> getOrganizationById(@PathVariable Long id) {

    return organizationRepository.findById(id);
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.Organization;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@Value
@RestController
@RequestMapping("/organization")
public class OrganizationController {

  OrganizationRepository organizationRepository;

  @GetMapping("/{id}")
  public Mono<Organization> getOrganizationById(@PathVariable Long id) {

    // return organizationRepository.findById(id);
    return Mono.error(RuntimeException::new);
  }
}

package tech.gruppone.stalkerserver.organization;

import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/organizations")
@RestController
public class OrganizationController {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

  private final OrganizationRepository organizationRepository = new OrganizationRepository();

  @GetMapping
  public Mono<WrappedOrganizations> getOrganizations() {
    logger.info("returning list of all organizations");

    Organization[] organizations = organizationRepository.findAllOrganizations();

//    can probably use producers for this
    var wrappedOrganizations = new WrappedOrganizations(organizations);

    return Mono.just(wrappedOrganizations);
  }

  @GetMapping("/{id}")
  Mono<Organization> getOrganizationById(@PathVariable int id) {

    return organizationRepository.findOrganizationById(id);
  }

  @Value
  public static class WrappedOrganizations {

    Organization[] organizations;
  }

//  @PutMapping("/{id}")
//  public Mono<Organization> putOrganizationById(@PathVariable int id, @RequestBody Organization updatedOrganization
//  ) {
//    Mono<Organization> organizationMono = organizationRepository.findOrganizationById(id);
//
//    organizationMono.subscribe(
//      value ->logger.info("found organization {}", value),
//      error -> logger.error(error.getMessage()),
//      () -> logger.info("completed without a value")
//    );
//
////    System.out.printf("body of request is {}", updatedOrganization);
//
//    return Mono.just(updatedOrganization);
//  }
}

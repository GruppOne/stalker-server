package tech.gruppone.stalkerserver.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/organizations")
@RestController
public class OrganizationController {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

  private final OrganizationRepository organizationRepository = new OrganizationRepository();


  @GetMapping
  public Flux<Organization> getOrganizations() {
    logger.info("returning list of all organizations");

    return organizationRepository.findAllOrganizations();
  }

  @GetMapping("/{id}")
  Mono<Organization> getOrganizationById(@PathVariable int id) {

    return organizationRepository.findOrganizationById(id);
  }

//  @PutMapping("/{id}")
//  public Mono<ServerResponse> putOrganizationById(@PathVariable int id, @RequestBody Organization updatedOrganization
//  ) {
//    Mono<Organization> organizationMono = organizationRepository.findOrganizationById(id);
//
//    System.out.printf("body of request is {}", updatedOrganization);
//
//    return Mono.just(new HttpServerResponse(HttpStatus.OK));
//  }
}

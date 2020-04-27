package tech.gruppone.stalker.server.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;
import tech.gruppone.stalker.server.repository.OrganizationRepository;

@RequestMapping("/organizations")
@RestController
@Value
public class OrganizationsController {

  OrganizationRepository organizationRepository;

  // TODO refactor this. it needs to return a valid json object: {"organizations":[...]}
  @GetMapping
  public Flux<Organization> getOrganizations() {
    return organizationRepository.findAll();
  }

  @PostMapping
  public Mono<Organization> createOrganizationById(@RequestBody String jsonString) throws IOException{
    Organization org = new ObjectMapper().readValue(jsonString, Organization.class);
    return organizationRepository.create(org.getName(), org.getDescription());
  }

}

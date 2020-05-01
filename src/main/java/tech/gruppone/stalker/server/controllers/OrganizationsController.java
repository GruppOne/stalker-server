package tech.gruppone.stalker.server.controllers;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.models.api.Organization;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

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
  public Mono<Organization> createOrganization(@RequestBody String jsonString) throws IOException{
    return Mono.empty();
    // Organization org = new ObjectMapper().readValue(jsonString, Organization.class);
    // return organizationRepository.create(org.getName(), org.getDescription());
  }

}

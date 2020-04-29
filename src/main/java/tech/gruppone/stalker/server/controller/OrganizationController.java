package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;
import tech.gruppone.stalker.server.repository.OrganizationRepository;

@RequestMapping("/organization")
@RestController
@Value
public class OrganizationController {

  OrganizationRepository organizationRepository;

  @GetMapping("/{id}")
  public Mono<Organization> getOrganizationById(@PathVariable Long id) {
    return organizationRepository.findById(id);
  }

  @PutMapping("/{id}")
  public Mono<Organization> updateOrganizationById(@PathVariable Long id, @RequestBody String jsonString) throws IOException{
    Organization org = new ObjectMapper().readValue(jsonString, Organization.class);
    return organizationRepository.update(id, org.getName(), org.getDescription());
  }


  @DeleteMapping("/{id}")
  public Mono<Organization> deleteOrganizationById(@PathVariable Long id){
    return organizationRepository.delete(id);
  }


  @GetMapping("/{id}/users/connections")
  public Flux<User> getUsersConnectedByOrganizationId(@PathVariable Long id){
    return organizationRepository.findAllUsers(id);
  }
}

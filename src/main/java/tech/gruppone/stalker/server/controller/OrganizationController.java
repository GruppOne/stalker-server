package tech.gruppone.stalker.server.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
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

  @PostMapping
  public Mono<Organization> createOrganizationById(@RequestBody String jsonString) throws IOException{
    Organization org = new ObjectMapper().readValue(jsonString, Organization.class);
    return organizationRepository.create(org.getName(), org.getDescription());
  }

  @DeleteMapping("/{id}")
  public Mono<Organization> deleteOrganizationById(@PathVariable Long id){
    return organizationRepository.delete(id);
  }
}

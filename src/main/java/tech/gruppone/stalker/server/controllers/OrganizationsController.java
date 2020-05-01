package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.OrganizationData;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationsResponse;
import tech.gruppone.stalker.server.model.api.responses.PostIdResponse;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@RequestMapping("/organizations")
@RestController
@Value
public class OrganizationsController {

  OrganizationRepository organizationRepository;

  //TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationsResponse> getOrganizations(){

    return Mono.error(new NotImplementedException());
    // return organizationRepository.findAllOrganizations();
  }

  //TODO
  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<PostIdResponse> createOrganization(@RequestBody final OrganizationData body) throws IOException{

    return Mono.error(new NotImplementedException());
    // return organizationRepository.create(...,..,..,..,..);
  }

}

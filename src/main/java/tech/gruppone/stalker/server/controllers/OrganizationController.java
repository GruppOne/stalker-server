package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.Organization;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdUsersConnectionsResponse;
import tech.gruppone.stalker.server.model.api.responses.UsersInsideOrganization;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

// @Log4j2
@RequestMapping("/organization/{organizationId}")
@RestController
@Value
public class OrganizationController {

  private OrganizationRepository organizationRepository;

  //TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<Organization> getOrganizationById(@PathVariable final Long organizationId) {

    return Mono.error(new NotImplementedException());
    // return organizationRepository.findById(organizationId);
  }

  //TODO
  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Organization> putOrganizationById(@PathVariable Long organizationId, @RequestBody final Organization body) throws IOException{

    return Mono.error(new NotImplementedException());
    // return organizationRepository.updateOrganizationById(organizationId, body.getName(), org.getDescription(), ...);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Organization> deleteOrganizationById(@PathVariable final Long organizationId){

    return organizationRepository.delete(organizationId);
  }

  //TODO
  @GetMapping("/users/connections")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationOrganizationIdUsersConnectionsResponse> getUsersConnectionsToOrganizationById(@PathVariable final Long organizationId){

    return Mono.error(new NotImplementedException());
    // return organizationRepository.findAllUsers(organizationId);
  }

  //TODO Is Alberto Cocco doing this endpoint in his branch? If YES, delete this endpoint by here!
  @PostMapping("/user/{userId}/role")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createRoleForAnUserById(@PathVariable("organizationId") final Long organizationId, @PathVariable("userId") final Long userId){

    return Mono.error(new NotImplementedException());
    // no function in OrganizationRepository
  }


  //TODO Is Alberto Cocco doing this endpoint in his branch? If YES, delete this endpoint by here!
  @DeleteMapping("/user/{userId}/role")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteRoleForAnUserById(@PathVariable("organizationId") final Long organizationId, @PathVariable("userId") final Long userId){

    return Mono.error(new NotImplementedException());
    // no function in OrganizationRepository
  }



  //TODO
  @GetMapping("/report")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> getReportByOrganizationId(@PathVariable final Long organizationId){

    return Mono.error(new NotImplementedException());
    // no function in OrganizationRepository
  }

  //TODO
  @GetMapping("/users/inside")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<UsersInsideOrganization> getPeopleNumberByOrganizationId(@PathVariable final Long organizationId){

    return Mono.error(new NotImplementedException());
    // no function in OrganizationRepository
  }



}

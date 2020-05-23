package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.services.OrganizationService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}")
public class OrganizationController {

  OrganizationService organizationService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<OrganizationDto> getOrganizationById(@PathVariable long organizationId) {
    return organizationService.findById(organizationId);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationById(@PathVariable final long id) {
    return Mono.error(NotImplementedException::new);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationById(@PathVariable final long id) {
    return Mono.error(NotImplementedException::new);
  }

  @GetMapping("/users/inside")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getOrganizationByIdUsersInside(@PathVariable final long id) {

    return Mono.error(NotImplementedException::new);
  }

  @GetMapping("/users/connections")
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationOrganizationIdUsersConnectionsResponse> getUsersConnectionsToOrganizationById(@PathVariable Long organizationId){
    return organizationService.findConnectedUsersByOrganizationId(organizationId).collectList().map(GetOrganizationOrganizationIdUsersConnectionsResponse::new);
   }

   @Value
   private static class GetOrganizationOrganizationIdUsersConnectionsResponse{
     List<UserDto> connectedUsers;
   }
}

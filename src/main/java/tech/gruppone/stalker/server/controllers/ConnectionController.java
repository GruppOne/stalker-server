package tech.gruppone.stalker.server.controllers;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.LdapConfiguration;
import tech.gruppone.stalker.server.model.api.responses.GetUserIdOrganizationsConnectionsResponse;

// @Log4j2
@Value
@RestController
@RequestMapping("/user/{userId}")
public class ConnectionController {

  private ConnectionRepository connectionRepository;

  //TODO
  @GetMapping("/organizations/connections")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Flux<GetUserIdOrganizationsConnectionsResponse> getConnectedOrganizationsByUserId(@PathVariable final Long userId){

    return Flux.error(new NotImplementedException());
    //return connectionRepository.findConnectedOrganizationsByUserId(userId);
  }

  //FIXME implement the part about ldapAuthentication --> DON'T ERASE THE IF(...) CODE!!
  @PostMapping("organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> connectUserToOrganizationById(@PathVariable("userId") final Long userId, @PathVariable("organizationId") final Long organizationId, @RequestBody final LdapConfiguration body) throws IOException{

    if (body.getHost().isEmpty() && body.getUsername().isEmpty() && body.getPassword().isEmpty()) return connectionRepository.postUserToOrganizationConnection(organizationId, userId);
    else return Mono.error(new NotImplementedException());
  }

  //TODO
  @DeleteMapping("organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserConnectionById(@PathVariable("userId") final Long userId, @PathVariable("organizationId") final Long organizationId) {

    return connectionRepository.deleteUserToOrganizationConnection(organizationId, userId);
  }


}

package tech.gruppone.stalker.server.controller;

import java.io.IOException;
// import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
// import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.AppConnection;
import tech.gruppone.stalker.server.repository.ConnectionsRepository;


@RestController
@Value
@RequestMapping("/user/{userId}/organization/{organizationId}/connection")
public class ConnectionsController {

  private ConnectionsRepository connectionsRepository;

  //TODO how to guarantee LDAP authentication if the organization corrensponds to a private organization?
  @PostMapping
  public Mono<AppConnection> connectUserToOrganizationById(@PathVariable("userId") final Long userId, @PathVariable("organizationId") final Long organizationId, @RequestBody String jsonString) throws IOException{
    // Connection connection = new ObjectMapper().readValue(jsonString, Connection.class);
    return connectionsRepository.postUserToOrganizationConnection(organizationId, userId);
  }

  @DeleteMapping
  public Mono<Void> deleteUserConnectionById(@PathVariable("userId") final Long userId, @PathVariable("organizationId") final Long organizationId) {
    return connectionsRepository.deleteUserToOrganizationConnection(organizationId, userId);
  }

}

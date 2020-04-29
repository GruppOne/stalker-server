package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Connection;
import tech.gruppone.stalker.server.repository.ConnectionRepository;

@RequestMapping("/user")
@RestController
@Value
public class ConnectionController {

  private ConnectionRepository connectionRepository;

  //TODO how to guarantee LDAP authentication if the organization corrensponds to a private organization?
  @PostMapping("/{id}/organizations/connections")
  public Mono<Connection> connectUserToOrganizationById(@PathVariable Long id, @RequestBody String jsonString) throws IOException{
    Connection connection = new ObjectMapper().readValue(jsonString, Connection.class);
    return connectionRepository.createConnectionById(connection.getOrganizationId(), id);
  }

  @DeleteMapping("/{userId}/organization/{organizationId}/connections")
  public Mono<Void> deleteUserConnectionById(@PathVariable("userId") Long userId, @PathVariable("organizationId") Long organizationId) {
    return connectionRepository.deleteConnectionById(organizationId, userId);
  }

}

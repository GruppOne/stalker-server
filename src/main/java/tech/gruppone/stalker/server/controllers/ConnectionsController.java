package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ConnectionsController {
  ConnectionRepository connectionRepository;

  // TODO implement me here!
  // @GetMapping("/organization/{organizationId}/users/connections")
  // public Mono<...> getOrganizationByIdUsersConnections(@PathVariable("organizationId") long
  // organizationId) {}

  @GetMapping("/user/{userId}/organizations/connections")
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUserByIdOrganizationsConnectionsResponse> getUserByIdOrganizationsConnections(
      @PathVariable("userId") long userId) {
    return connectionRepository
        .findConnectedOrganizationsByUserId(userId)
        .collectList()
        .map(GetUserByIdOrganizationsConnectionsResponse::new);
  }

  @Value
  @AllArgsConstructor
  public static class GetUserByIdOrganizationsConnectionsResponse {
    List<Long> connectedOrganizationsIds;
  }
}

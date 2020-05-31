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
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.services.ConnectionService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ConnectionsController {

  ConnectionRepository connectionRepository;
  ConnectionService connectionService;

  @GetMapping("/organization/{organizationId}/users/connections")
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationOrganizationIdUsersConnectionsResponse>
      getOrganizationByIdUsersConnections(
          @PathVariable("organizationId") final long organizationId) {

    return connectionService
        .findConnectedUsersByOrganizationId(organizationId)
        .collectList()
        .map(GetOrganizationOrganizationIdUsersConnectionsResponse::new);
  }

  @GetMapping("/user/{userId}/organizations/connections")
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUserByIdOrganizationsConnectionsResponse> getUserByIdOrganizationsConnections(
      @PathVariable("userId") final long userId) {
    return connectionRepository
        .findConnectedOrganizationIdsByUserId(userId)
        .collectList()
        .map(GetUserByIdOrganizationsConnectionsResponse::new);
  }

  @Value
  private static class GetOrganizationOrganizationIdUsersConnectionsResponse {
    List<UserDto> connectedUsers;
  }

  @Value
  @AllArgsConstructor
  public static class GetUserByIdOrganizationsConnectionsResponse {
    List<Long> connectedOrganizationsIds;
  }
}

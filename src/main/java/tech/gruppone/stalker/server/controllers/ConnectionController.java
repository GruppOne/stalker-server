package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}")
public class ConnectionController {

  ConnectionRepository connectionRepository;

  @PostMapping("/organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createUserConnection(
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") long organizationId) {
    // TODO manage LDAP properties if the organization is private
    return connectionRepository.createUserConnection(userId, organizationId);
  }

  @DeleteMapping("/organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserConnectionToOrganization(
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") long organizationId) {
    return connectionRepository.deleteUserConnection(userId, organizationId);
  }

  @GetMapping("/organizations/connections")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUserIdOrganizationsConnectionsResponse> getConnectedOrganizationsByUserId(
      @PathVariable("userId") long userId) {
    return connectionRepository
        .findConnectedOrganizationsByUserId(userId)
        .collectList()
        .map(GetUserIdOrganizationsConnectionsResponse::new);
  }

  @AllArgsConstructor
  @Value
  public static class GetUserIdOrganizationsConnectionsResponse {
    List<Long> connectedOrganizationsIds;
  }
}

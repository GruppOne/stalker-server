package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}/organization/{organizationId}/connection")
public class ConnectionController {

  ConnectionRepository connectionRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createUserConnection(
      @PathVariable("userId") long userId, @PathVariable("organizationId") long organizationId) {
    // TODO manage LDAP properties if the organization is private
    return connectionRepository.createUserConnection(userId, organizationId);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserConnectionToOrganization(
      @PathVariable("userId") long userId, @PathVariable("organizationId") long organizationId) {
    return connectionRepository.deleteUserConnection(userId, organizationId);
  }
}

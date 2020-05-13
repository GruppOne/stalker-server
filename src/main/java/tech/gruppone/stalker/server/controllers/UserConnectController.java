package tech.gruppone.stalker.server.controllers;


import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Connection;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@RestController
@Value
@NonFinal
public class UserConnectController {

  @NonNull
  ConnectionRepository connectionRepository;

  @PostMapping("/user/{userId}/organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.CREATED)
  Mono<Void> connectUserToOrganization(
    @PathVariable("userId") long userId, @PathVariable("organizationId") long organizationId
  ) {
    // the service will manage LDAP properties if the organization is private
    return connectionRepository.connectUserToOrganization(userId, organizationId, LocalDateTime.now());
  }

  @DeleteMapping("/user/{userId}/organization/{organizationId}/connection")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Mono<Void> deleteUserConnectionToOrganization(@PathVariable("userId") long userId, @PathVariable("organizationId") long organizationId){
    return connectionRepository.deleteUserConnectionToOrganization(userId, organizationId);
  }
}

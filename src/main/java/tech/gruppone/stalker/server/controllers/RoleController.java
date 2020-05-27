package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/user/{userId}/role")
public class RoleController {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Throwable> postOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

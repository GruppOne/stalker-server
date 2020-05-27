package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/user/{userId}/role")
public class RoleController {

  @DeleteMapping
  public Mono<Throwable> deleteOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PostMapping
  public Mono<Throwable> postOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PutMapping
  public Mono<Throwable> putOrganizationByIdUserByIdRole(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

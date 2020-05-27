package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class HistoryController {

  @GetMapping("/organization/{organizationId}/user/{userId}/history")
  public Mono<Throwable> getOrganizationByIdUserByIdHistory(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @GetMapping("/user/{userId}/history")
  public Mono<Throwable> getUserByIdHistory(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

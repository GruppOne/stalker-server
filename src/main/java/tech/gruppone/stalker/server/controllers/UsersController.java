package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UsersController {

  @GetMapping
  public Mono<Throwable> getUsers(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PostMapping
  public Mono<Throwable> postUsers(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UsersController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getUsers(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Throwable> postUsers(@PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

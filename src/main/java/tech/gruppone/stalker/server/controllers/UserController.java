package tech.gruppone.stalker.server.controllers;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Value
@RestController
@RequestMapping("/users")
public class UserController {

  UserRepository userRepository;

  @GetMapping
  public Flux<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable Long id) {

    return userRepository.findById(id);
  }

  // TODO implement
  // TODO should return valid json
  @GetMapping("/{id}/subscribed")
  public Flux<Integer> getSubscribedOrganizations(ServerHttpResponse response,@PathVariable Long id) {

    response.setStatusCode(HttpStatus.NOT_IMPLEMENTED);

    return Flux.empty();
  }
}

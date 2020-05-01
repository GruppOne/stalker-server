package tech.gruppone.stalker.server.controller;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repository.RoleRepository;
import tech.gruppone.stalker.server.repository.UserRepository;
import tech.gruppone.stalker.server.model.UserRoles;

@RestController
@Log4j2
@RequestMapping("/users")
@Data
public class UserController {


  @Autowired
  @Getter(AccessLevel.NONE)
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;


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

  @GetMapping("/roles/{username}")
  public Flux<UserRoles> find (@PathVariable String username){
     return roleRepository.findUserRoles(username);
  }
}

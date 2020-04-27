package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repository.UserRepository;

@Value
@RestController
@RequestMapping("/user")
public class UserController {

  UserRepository userRepository;

  //@PostMapping("/login")

  //@PostMapping("/password/recovery")

  //PAY ATTENTION: this endpoint isn't specified in api documentation: is it needed?
  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable Long id) {

    return userRepository.findById(id);
  }

  @PutMapping("/{id}")
  public Mono<User> updateUserById(@PathVariable Long id, @RequestBody String jsonString) throws IOException{

    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.updateUserData(id, user.getFirstName(), user.getLastName()/*, user.getBirthDate()*/);
  }

  //@DeleteMapping("/{id}")

  //@PostMapping("/{id}/logout")

  //@GetMapping("/{id}/organizations/connections")
  //@PostMapping("/{id}/organizations/connections")
  //@DeleteMapping("/{id}/organizations/connections")

  //@GetMapping("/{id}/places/history")

  // // TODO implement
  // // TODO should return valid json
  // @GetMapping("/{id}/subscribed")
  // public Flux<Integer> getSubscribedOrganizations(ServerHttpResponse response,@PathVariable Long id) {

  //   response.setStatusCode(HttpStatus.NOT_IMPLEMENTED);

  //   return Flux.empty();
  // }
}

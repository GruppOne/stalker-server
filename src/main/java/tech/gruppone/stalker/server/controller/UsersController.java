package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/users")
public class UsersController {

  UserRepository userRepository;

  @GetMapping
  public Flux<User> getUsers() {
    return userRepository.findAll();
  }

  @PostMapping
  public Mono<User> createUser(@RequestBody String jsonString) throws IOException{

    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.createUser(user.getEmail(),user.getPassword());
    //TODO how to insert user data in UserData
  }


}

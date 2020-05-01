package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.model.api.GetUsersResponse;
import tech.gruppone.stalker.server.repository.UserRepository;

@Value
@RestController
@RequestMapping("/users")
public class UsersController {

  UserRepository userRepository;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUsersResponse> getUsers() {

    return Mono.error(new NotImplementedException());
    // return userRepository.findAllUsers();
  }

  //TODO verify if @RequestBody User user works
  @PostMapping
  public Mono<User> createUser(@RequestBody String jsonString) throws IOException{
    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.createUser(user.getEmail(),user.getPassword(), user.getFirstName(), user.getLastName(), user.getBirthDate());
  }

}

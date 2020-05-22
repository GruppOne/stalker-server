package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@Value
@RestController
@RequestMapping("/users")
public class UsersController {

  UserRepository userRepository;
  UserService userService;

  // TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<UsersResponse> getUsers() {

    return userService.findAll().collectList().map(UsersResponse::new);
  }

  @Value
  private static class UsersResponse {
    List<UserDto> users;
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<EncodedJwtDto> createUser(@RequestBody UserDataWithLoginData signUp) {

    return userService.signUpUser(signUp).map(EncodedJwtDto::new);
  }

  @Value
  public static class EncodedJwtDto {
    String jwt;
  }
}

package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.EncodedJwtDto;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.services.SignUpService;
import tech.gruppone.stalker.server.services.UserService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UsersController {

  UserService userService;
  SignUpService signUpService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<UsersResponse> getUsers() {

    return userService.findAll().collectList().map(UsersResponse::new);
  }

  @Value
  private static class UsersResponse {
    List<UserDto> users;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<EncodedJwtDto> postUsers(@RequestBody UserDataWithLoginData signUp) {

    return signUpService
        .createNewUser(signUp.getLoginData(), signUp.getUserData())
        .map(EncodedJwtDto::new);
  }

  @Value
  public static class UserDataWithLoginData {

    @NonNull LoginDataDto loginData;

    @NonNull UserDataDto userData;
  }
}

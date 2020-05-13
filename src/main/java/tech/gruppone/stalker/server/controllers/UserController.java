package tech.gruppone.stalker.server.controllers;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@Value
@RestController
@RequestMapping("/user/{userId}")
public class UserController {

  UserRepository userRepository;
  UserService userService;

  // // GET /users
  // @GetMapping
  // public Flux<UserDto> getUsers() {
  //   return userRepository.findAll();
  // }

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserDto> getUserById(@PathVariable final Long userId) {
    //return Mono.empty();
    //return userRepository.findById(userId);
    return userService.read(userId);
  }

  // @PutMapping
  // @ResponseStatus(HttpStatus.NO_CONTENT)
  // public Mono<Void> updateUserById(@PathVariable final Long userId, @RequestBody final UserDataWithLoginData body) throws IOException {

  //   return Mono.error(new NotImplementedException());
  //   // return userRepository.updateUser(body.getUserData().getFirstName(), body.getUserData().getLastName(), body.getUserData().getBirthDate(), userId);
  // }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserById(@PathVariable final Long userId) {

    return userRepository.deleteUserById(userId);
  }

}

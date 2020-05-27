package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UpdatePasswordDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}")
public class UserController {

  // FIXME should not depend on both repo and service
  UserRepository userRepository;
  UserService userService;

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserById(@PathVariable("userId") final Long userId) {

    return userRepository.deleteUserById(userId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserDto> getUserById(@PathVariable("userId") final Long userId) {

    return userService.findById(userId);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putUserById(@PathVariable("userId") final Long userId, @RequestBody UserDataDto userDataDto){

    return userService.putUserById(userDataDto, userId);
  }

  @PutMapping("/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putUserByIdPassword(
      @RequestBody UpdatePasswordDto updatePasswordDto, @PathVariable final Long userId) {
    return userService.updatePassword(updatePasswordDto, userId);
  }
}

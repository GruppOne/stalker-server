package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.TimeInsidePlaces;
import tech.gruppone.stalker.server.model.api.User;
import tech.gruppone.stalker.server.model.api.requests.PutUserIdRequest;

import tech.gruppone.stalker.server.repositories.UserRepository;

// @Log4j2
@Value
@RestController
@RequestMapping("/user/{id}")
public class UserController {

  private UserRepository userRepository;

  //TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<User> getUserById(@PathVariable final Long id) {

    return Mono.error(new NotImplementedException());
    // return userRepository.findById(id);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> updateUserById(@PathVariable final Long id, @RequestBody final PutUserIdRequest body) throws IOException {

    return Mono.error(new NotImplementedException());
    // return userRepository.updateUser(body.getUserData().getFirstName(), body.getUserData().getLastName(), body.getUserData().getBirthDate(), id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserById(@PathVariable final Long id) {

    return userRepository.deleteUserById(id);
  }

  //TODO --> Perhaps useless, because it's not a server responsability, but an app responsability. What do you say, Luca??
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> logoutUser(@PathVariable final Long id){

    return Mono.error(new NotImplementedException());
    // no function in UserRepository
  }

  //TODO in InfluxDB
  @GetMapping("/places/history")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> getHistoryUser(@PathVariable final Long id){

    return Mono.error(new NotImplementedException());
    // no function in UserRepository
  }

  //TODO in InfluxDB
  @GetMapping("/time/inside")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<TimeInsidePlaces> getUserTimeInside(@PathVariable final Long id){

    return Mono.error(new NotImplementedException());
    // no function in UserRepository
  }

}

package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
// import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.models.api.request.PostUsersRequest;
import tech.gruppone.stalker.server.models.api.response.GetUsersResponse;
import tech.gruppone.stalker.server.models.api.User;
import tech.gruppone.stalker.server.repositories.UserRepository;

// @Log4j2
@Value
@RestController
@RequestMapping("/users")
public class UsersController {

  UserRepository userRepository;

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUsersResponse> getUsers() {

    return Mono.error(new NotImplementedException());
    //return userRepository.findAllUsers();
  }

  @PostMapping
  @ResponseBody
  public Mono<User> createUser(@RequestBody PostUsersRequest signUp) throws IOException{

    return Mono.error(new UnsupportedOperationException("NOT IMPLEMENTED YET"));

    // THIS METHOD IS ALMOST GOOD --> CHECK THE TODOS
    // //TODO how to check if the new user is inserting a new mail (not present on DB)
    // // userRepository.findByEmail(signUp.getLoginData().getEmail()).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is not valid or is already registered")));
    // if(signUp.getUserData().getEmail() != null &&
    //     signUp.getLoginData().getPassword().length() == 128 &&
    //     signUp.getUserData().getFirstName() != null &&
    //     signUp.getUserData().getLastName() != null &&
    //     signUp.getUserData().getBirthDate() != null){
    //   userRepository.createUser(signUp.getLoginData().getEmail(), signUp.getLoginData().getPassword(), signUp.getUserData().getFirstName(), signUp.getUserData().getLastName(), signUp.getUserData().getBirthDate());
    //   //TODO this endpoint return a generic id
    //   //return userRepository.findIdByMail(signUp.getUserData().getEmail());
    // }else{
    //   log.error("JSON malformed");
    //   return Mono.empty();
    // }

  }

}

package tech.gruppone.stalker.server.controllers;

import java.io.IOException;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.requests.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.api.responses.GetUsersResponse;
import tech.gruppone.stalker.server.model.api.responses.PostIdResponse;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Value
@RestController
@RequestMapping("/users")
public class UsersController {

  UserRepository userRepository;

  // TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetUsersResponse> getUsers() {

    return Mono.error(new NotImplementedException());
    // return userRepository.findAllUsers();
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<PostIdResponse> createUser(@RequestBody final UserDataWithLoginData signUp)
      throws IOException {

    // //--------------------------------------------DON'T EREASE IT!! FIX ALL THE FIXME AND THE
    // ENDPOINT WILL WORK------------------------------------------
    // //FIXME how to check if the new user is inserting a new mail (not present on DB)?
    // // userRepository.findByEmail(signUp.getLoginData().getEmail()).switchIfEmpty(Mono.error(new
    // ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is not valid or is already
    // registered")));
    // if((signUp.getLoginData().getEmail() != null) &&
    //     (signUp.getLoginData().getPassword().length() == 128) &&
    //     (signUp.getUserData().getFirstName() != null) &&
    //     (signUp.getUserData().getLastName() != null) &&
    //     (signUp.getUserData().getBirthDate() != null)){

    //   userRepository.createUser(signUp.getLoginData().getEmail(),
    // signUp.getLoginData().getPassword(), signUp.getUserData().getFirstName(),
    // signUp.getUserData().getLastName(), signUp.getUserData().getBirthDate());
    //   //FIXME this function isn't able to return a PostUsersResponse id. Fix it.
    //   return userRepository.findIdByMail(signUp.getUserData().getEmail());

    // }else{
    //   //TODO specify better the error, if is possible
    //   log.error("JSON malformed");
    //   return Mono.empty();

    // }
    // //---------------------------------------------------------------------------------------------------------------------------------------------

    return Mono.error(new NotImplementedException());
  }
}

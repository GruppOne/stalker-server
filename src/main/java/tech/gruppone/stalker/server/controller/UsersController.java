package tech.gruppone.stalker.server.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.ServerHttpResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
// import lombok.extern.log4j.Log4j2;
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
    //TODO solve the problem of null fields
    return userRepository.findAllUsers();
  }

  //TODO veify if @RequestBody User user works
  @PostMapping
  public Mono<User> createUser(@RequestBody String jsonString) throws IOException{
    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.createUser(user.getEmail(),user.getPassword(), user.getFirstName(), user.getLastName(), user.getBirthDate());
  }

}

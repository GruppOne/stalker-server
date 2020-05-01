package tech.gruppone.stalker.server.controllers;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.ServerHttpResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.models.api.User;
import tech.gruppone.stalker.server.repositories.UserRepository;

// @Log4j2
@Value
@RestController
@RequestMapping("/user/{id}")
public class UserController {

  private UserRepository userRepository;

  @GetMapping
  public Mono<User> getUserById(@PathVariable final Long id) {
    return userRepository.findById(id);
  }

  @PutMapping
  public Mono<User> updateUserById(@PathVariable final Long id, @RequestBody final String jsonString) throws IOException {
    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.updateUser(user.getUserData().getFirstName(), user.getUserData().getLastName(), user.getUserData().getBirthDate(), id);
  }

  @DeleteMapping
  public Mono<Void> deleteUserById(@PathVariable final Long id) {
    return userRepository.deleteUserById(id);
  }

  //TODO Perhaps useless, because it's not a server responsability, but an app responsability. What do you say??
  @PostMapping("/logout")
  public void logoutUser(@PathVariable final Long id){
    throw new UnsupportedOperationException("NOT IMPLEMENTED YET");
  }

  //TODO InfluxDB
  @GetMapping("/places/history")
  public void getHistoryUser(@PathVariable final Long id){
    throw new UnsupportedOperationException("NOT IMPLEMENTED YET");
  }

}

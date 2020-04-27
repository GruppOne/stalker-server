package tech.gruppone.stalker.server.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
// import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repository.UserRepository;

@Value
@RestController
@RequestMapping("/user")
public class UserController {

  UserRepository userRepository;

  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable Long id) {
    return userRepository.findById(id);
  }

  @PutMapping("/{id}")
  public Mono<User> updateUserById(@PathVariable Long id, @RequestBody String jsonString) throws IOException{
    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.updateUser(user.getFirstName(), user.getLastName(), user.getBirthDate(), id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteUserById(@PathVariable Long id){
    return userRepository.deleteById(id);
  }

  //TODO Security?
  //@PostMapping("/login")
  //@PostMapping("/password/recovery")
  //@PostMapping("/{userId}/logout")

  //TODO This three in InfluxDB??
  //@GetMapping("/{userId}/organizations/connections")
  //@PostMapping("/{userId}/organizations/connections")
  //@DeleteMapping("/{userId}/organizations/connections")

  //TODO InfluxDB
  //@GetMapping("/{userId}/places/history")

}

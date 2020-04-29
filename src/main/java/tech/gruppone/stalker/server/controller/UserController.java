package tech.gruppone.stalker.server.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.ServerHttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.SmtpMailSender;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repository.UserRepository;

@Value
@RestController
@Log4j2
@RequestMapping("/user")
public class UserController {

  private UserRepository userRepository;
  @Autowired
  private SmtpMailSender smtpMailSender;

  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable final Long id) {
    return userRepository.findById(id);
  }

  @PutMapping("/{id}")
  public Mono<User> updateUserById(@PathVariable final Long id, @RequestBody final String jsonString) throws IOException {
    User user = new ObjectMapper().readValue(jsonString, User.class);
    return userRepository.updateUser(user.getFirstName(), user.getLastName(), user.getBirthDate(), id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteUserById(@PathVariable final Long id) {
    return userRepository.deleteUserById(id);
  }

  @PostMapping("/password/recovery")
  public void recoveryUserPassword(@RequestBody final String jsonString) throws IOException, MessagingException {
    User user = new ObjectMapper().readValue(jsonString, User.class);
    //TODO add control to verify unique email in the database --> findByEmail(user.getEmail());
    smtpMailSender.send(user.getEmail(), "Recovery password for Stalker", "Here below there's the link to complete the recovery passowrd's operation.\n\nThe staff\nGruppone");
    log.info("The mail has been sent correctly to {} ", user.getEmail());

  }

  //TODO Perhaps useless, because it's not a server responsability, but an app responsability
  //@PostMapping("/{id}/logout")

  //TODO InfluxDB
  //@GetMapping("/{id}/places/history")

}

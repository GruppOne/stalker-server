package tech.gruppone.stalker.server.controllers;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.UnauthenticatedUser;
import tech.gruppone.stalker.server.services.LoginService;

@Data
@RestController
public class AuthenticationController {

  @Autowired
  LoginService loginService;

  @PostMapping("/user/login")
  public Mono<ResponseEntity<?>> login(@RequestBody UnauthenticatedUser unauthenticatedUser){
        return loginService.logUser(unauthenticatedUser);
  }

}

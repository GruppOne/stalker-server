package tech.gruppone.stalker.server.controller;


import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.UnauthenticatedUser;
import tech.gruppone.stalker.server.repository.UserRepository;
import tech.gruppone.stalker.server.security.JwtUtil;
import tech.gruppone.stalker.server.service.LoginService;

@RestController
@Data

public class AuthenticationController {

  @Autowired
  LoginService loginService;

  @PostMapping("/login")
  public Mono<ResponseEntity<?>> login(@RequestBody UnauthenticatedUser unauthenticatedUser){
        return loginService.logUser(unauthenticatedUser);
  }

}

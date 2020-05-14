package tech.gruppone.stalker.server.repositories;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.services.LoginService;


@Data
@RestController
public class AuthenticationController {

  LoginService loginService;

  @PostMapping("/user/login")
  public Mono<String> login(@RequestBody LoginDataDto loginData){
        return loginService.logUser(loginData);
  }

}

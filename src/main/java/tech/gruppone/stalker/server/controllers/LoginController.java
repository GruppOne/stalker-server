package tech.gruppone.stalker.server.controllers;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.EncodedJwtDto;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.services.LoginService;

@RestController
@Value
public class LoginController {

  LoginService loginService;

  @PostMapping("/user/login")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<EncodedJwtDto> postUserLogin(@RequestBody LoginDataDto loginDataDto) {
    return loginService.logUser(loginDataDto);
  }
}

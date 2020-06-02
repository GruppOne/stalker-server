package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.EncodedJwtDto;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.services.LoginService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class LoginController {

  LoginService loginService;

  // TODO should throw InvalidUserCredentialsException
  @PostMapping("/user/login")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<EncodedJwtDto> postUserLogin(@RequestBody final LoginDataDto loginDataDto) {
    return loginService
        .login(loginDataDto.getEmail(), loginDataDto.getPassword())
        .map(EncodedJwtDto::new);
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.services.UserService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class PasswordController {
  UserService userService;

  // TODO should throw InvalidEmailException!
  @PostMapping("/user/password/recovery")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> postUserPasswordRecovery() {

    return Mono.error(NotImplementedException::new);
  }

  @PutMapping("/user/{userId}/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putUserByIdPassword(
      @RequestBody final PutUserByIdPasswordRequestBody requestBody,
      @PathVariable final Long userId) {

    return userService.updatePassword(
        requestBody.getOldPassword(), requestBody.getNewPassword(), userId);
  }

  @Value
  public static class PutUserByIdPasswordRequestBody {
    @NonNull String oldPassword;
    @NonNull String newPassword;
  }
}

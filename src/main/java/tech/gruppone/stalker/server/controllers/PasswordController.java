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
import tech.gruppone.stalker.server.services.PasswordService;
import tech.gruppone.stalker.server.services.UserService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class PasswordController {
  PasswordService passwordService;
  UserService userService;

  @PostMapping("/user/password/recovery")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> postUserPasswordRecovery(
      @RequestBody final PostUserPasswordRecoveryRequestBody requestBody) {

    passwordService.resetPassword(requestBody.getEmail());

    // this method should never throw errors, even if no users are registered with this email.
    return Mono.empty();
  }

  @Value
  static class PostUserPasswordRecoveryRequestBody {
    @NonNull String email;
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
  static class PutUserByIdPasswordRequestBody {
    @NonNull String oldPassword;
    @NonNull String newPassword;
  }
}

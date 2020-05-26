package tech.gruppone.stalker.server.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.SmtpMailSenderConfiguration;
import tech.gruppone.stalker.server.exceptions.EmailErrorException;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class PasswordController {
  UserService userService;

  /*
    FOLLOW THIS STEPS to work properly with this endpoint and to not get an MailAuthenticationException error:
    Since we are using Gmail to send email, we need to enable "less secure access" on the Gmail account.
    - Log out from all the Google accounts from the browser. Login in to the gmail account configured in application.properties.
    - Then click on https://myaccount.google.com/lesssecureapps and turn on access for less secure apps.
     - Alternately, if the link up doesn't work, go on https://accounts.google.com/b/0/DisplayUnlockCaptcha.
    Now, you invoke /user/password/recovery endpoint and you'll receive an mail by GruppOne!
  */

  UserRepository userRepository;

  SmtpMailSenderConfiguration smtpMailSender;

  // TODO move this to the corresponding service (not a controller's responsibility)
  private static final String EMAIL_BODY =
      String.join(
          System.lineSeparator(),
          "You're received this mail because you want to recovery your Stalker's password.",
          "Follow this link: www.stalker.com.",
          "The team GruppOne");

  // TODO should throw InvalidEmailException!
  @PostMapping("/user/password/recovery")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> postUserPasswordRecovery(
      @RequestBody final PostUserPasswordRecoveryRequestBody requestBody) {

    return userRepository
        .findByEmail(requestBody.getEmail())
        .filter(result -> result.getEmail().equals(requestBody.getEmail()))
        .map(
            result -> {
              try {
                smtpMailSender.send(
                    requestBody.getEmail(), "Stalker Recovery password", EMAIL_BODY);
              } catch (MessagingException e) {
                log.info(e.getStackTrace());
              }
              log.info("The mail has been sent correctly to {} ", requestBody.getEmail());
              return Mono.empty();
            })
        .switchIfEmpty(Mono.error(new EmailErrorException()))
        .then();
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

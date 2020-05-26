package tech.gruppone.stalker.server.controllers;

import java.io.IOException;
import javax.mail.MessagingException;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.SmtpMailSenderConfiguration;
import tech.gruppone.stalker.server.exceptions.EmailErrorException;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Value
@RestController
@Log4j2
@RequestMapping("/user/password/recovery")
public class PasswordRecoveryController {

  /*
    FOLLOW THIS STEPS to work properly with this endpoint and to not get an MailAuthenticationException error:
    Since we are using Gmail to send email, we need to enable "less secure access" on the Gmail account.
    - Log out from all the Google accounts from the browser. Login in to the gmail account configured in application.properties.
    - Then click on https://myaccount.google.com/lesssecureapps and turn on access for less secure apps.
     - Alternately, if the link up doesn't work, go on https://accounts.google.com/b/0/DisplayUnlockCaptcha.
    Now, you invoke /user/password/recovery endpoint and you'll receive an mail by GruppOne!
  */

  private UserRepository userRepository;

  @Autowired private SmtpMailSenderConfiguration smtpMailSender;

  private static final String EMAIL_BODY =
      String.join(
          System.lineSeparator(),
          "You're received this mail because you want to recovery your Stalker's password.",
          "Follow this link: www.stalker.com.",
          "The team GruppOne");

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> recoveryUserPassword(
      @RequestBody final PostUserPasswordRecoveryRequest requestBody) throws IOException {

    return userRepository
        .findByEmail(requestBody.getEmail())
        .filter(result -> result.getEmail().equals(requestBody.getEmail()))
        .map(
            result -> {
              try {
                smtpMailSender.send(
                    requestBody.getEmail(), "Stalker Recovery password", EMAIL_BODY);
              } catch (MessagingException e) {
                e.printStackTrace();
              }
              log.info("The mail has been sent correctly to {} ", requestBody.getEmail());
              return Mono.empty();
            })
        .switchIfEmpty(
            Mono.error(
                new EmailErrorException(
                    "It is not possible to execute this operation: email not registered in Stalker.")))
        .then();
  }

  @Value
  private static class PostUserPasswordRecoveryRequest {
    String email;
  }
}

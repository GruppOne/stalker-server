package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import tech.gruppone.stalker.server.configuration.SmtpMailSenderConfiguration;
import tech.gruppone.stalker.server.model.api.UserData;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Value
@RestController
@Log4j2
@RequestMapping("/user/password/recovery")
public class RecoveryPasswordController {

  /*
    FOLLOW THIS STEPS to work properly with this endpoint and to not get an MailAuthenticationException error:

    Since we are using Gmail to send email, we need to enable "less secure access" on the Gmail account.
    - Log out from all the Google accounts from the browser. Login in to the gmail account configured in application.properties.
    - Then click on https://myaccount.google.com/lesssecureapps and turn on access for less secure apps.
     - Alternately, if the link up doesn't work, go on https://accounts.google.com/b/0/DisplayUnlockCaptcha.

    Now, you invoke /user/password/recovery endpoint and you'll receive an mail by GruppOne!
  */

  private UserRepository userRepository;

  @Autowired
  private SmtpMailSenderConfiguration smtpMailSender;

  private static final String EMAIL_BODY = String.join(System.lineSeparator(),
                                "Lorem ipsum first line",
                                "the second line",
                                "final remarks.");

  //--------------------------------------------DON'T EREASE IT!! FIX TODOS AND THE ENDPOINT WILL WORK------------------------------------------
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void recoveryUserPassword(@RequestBody final UserData userData) throws IOException, MessagingException {

    //FIXME add control to verify unique email in the database --> findByEmail(user.getEmail());
    smtpMailSender.send(userData.getEmail(), "Recovery password for Stalker", EMAIL_BODY);
    log.info("The mail has been sent correctly to {} ", userData.getEmail());
  }
  //---------------------------------------------------------------------------------------------------------------------------------------------

}

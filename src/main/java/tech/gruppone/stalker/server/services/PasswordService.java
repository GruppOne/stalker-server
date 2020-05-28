package tech.gruppone.stalker.server.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.InvalidEmailException;
import tech.gruppone.stalker.server.repositories.UserRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Log4j2
public class PasswordService {

  UserRepository userRepository;

  JavaMailSender javaMailSender;
  public void send(String to, String subject, String body) throws MessagingException {

    MimeMessage message = javaMailSender.createMimeMessage();

    // SSL Certificate.
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    // Multipart messages.
    helper.setSubject(subject);
    helper.setTo(to);
    helper.setText(body, true);

    javaMailSender.send(message);
  }

  private static final String EMAIL_BODY =
      String.join(
          System.lineSeparator(),
          "You're receiving this email because you asked to recover your Stalker account's password.",
          "You can change password to the next login in Stalker.",
          "The team GruppOne.");
  public Mono<Void> sendPasswordRecoveryEmail(String userEmail) {
    /*
    FOLLOW THESE STEPS to work properly with this endpoint and to not get an MailAuthenticationException error:
    Since we are using Gmail to send email, we need to enable "less secure access" on the Gmail account.
    - Log out from all the Google accounts from the browser. Login in to the gmail account configured in application.properties.
    - Then click on https://myaccount.google.com/lesssecureapps and turn on access for less secure apps.
     - Alternately, if the link up doesn't work, go on https://accounts.google.com/b/0/DisplayUnlockCaptcha.
    Now, you invoke /user/password/recovery endpoint and you'll receive an mail by GruppOne!
    */
    return userRepository
        .findByEmail(userEmail)
        .filter(result -> result.getEmail().equals(userEmail))
        .map(
            result -> {
              try {
                send(
                  userEmail, "Stalker Recovery Password", EMAIL_BODY);
              } catch (MessagingException e) {
                log.info(e.getStackTrace());
              }
              log.info("The mail has been sent correctly to {} ", userEmail);
              return Mono.empty();
            })
        .switchIfEmpty(Mono.error(new InvalidEmailException()))
        .then();
  }
}

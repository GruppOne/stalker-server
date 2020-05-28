package tech.gruppone.stalker.server.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
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

  public void sendEmail(String to, String subject, String body) throws MessagingException {

    MimeMessage message = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setSubject(subject);
    helper.setTo(to);
    helper.setText(body, true);

    javaMailSender.send(message);
  }

  private static String encryptPassword(String input) throws NoSuchAlgorithmException {

    MessageDigest md = MessageDigest.getInstance("SHA-512");
    byte[] messageDigest = md.digest(input.getBytes());
    BigInteger no = new BigInteger(1, messageDigest);
    String hashtext = no.toString(16);
    while (hashtext.length() < 32) hashtext = "0" + hashtext;

    return hashtext;
  }

  public Mono<Void> sendPasswordRecoveryEmail(String userEmail) throws NoSuchAlgorithmException {
    /*
    FOLLOW THESE STEPS to work properly with this endpoint and to not get an MailAuthenticationException error:
    Since we are using Gmail to send email, we need to enable "less secure access" on the Gmail account.
    - Log out from all the Google accounts from the browser. Login in to the gmail account configured in application.properties.
    - Then click on https://myaccount.google.com/lesssecureapps and turn on access for less secure apps.
     - Alternately, if the link up doesn't work, go on https://accounts.google.com/b/0/DisplayUnlockCaptcha.
    Now, you invoke /user/password/recovery endpoint and you'll receive an mail by GruppOne!
    */
    String newPassword = Long.toHexString(Double.doubleToLongBits(Math.random()));

    return userRepository
        .findByEmailAndUpdatePassword(userEmail, encryptPassword(newPassword))
        .filter(result -> result.getEmail().equals(userEmail))
        .map(
            result -> {
              try {
                sendEmail(
                    userEmail,
                    "Stalker Recovery Password",
                    "Hi, you're receiving this email because you asked to recover your Stalker account's password.<br/> Now your old password has been resetted, and the new password associated to your Stalker account is: <b>"
                        + newPassword
                        + "</b>.<br/>You can change to a new password on your next login in Stalker.<br/><br/>The GruppOne team.");
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

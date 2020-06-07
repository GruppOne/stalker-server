package tech.gruppone.stalker.server.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PasswordService {

  JavaMailSender javaMailSender;

  UserRepository userRepository;

  public Mono<Void> updatePassword(
      final String oldPassword, final String newPassword, final Long userId) {

    if (newPassword.isBlank()) {
      return Mono.error(BadRequestException::new);
    }

    return userRepository
        .findById(userId)
        .filter(userDao -> userDao.getPassword().equals(oldPassword))
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .flatMap(userDao -> userRepository.save(userDao.withPassword(newPassword)))
        .then();
  }

  public void resetPassword(final String userEmail) {
    final String newPassword = generateRandomSecurePassword();
    final String hashedNewPassword = hashPassword(newPassword);

    userRepository
        .findByEmail(userEmail)
        .flatMap(user -> userRepository.save(user.withPassword(hashedNewPassword)))
        .doOnSuccess(nullVariable -> sendEmail(userEmail, newPassword))
        .subscribe();
  }

  private static final String EMAIL_SUBJECT = "Stalker password recovery";
  private static final String EMAIL_BODY_TEMPLATE =
      String.join(
          System.getProperty("line.separator"),
          "Hi,",
          "you're receiving this email because you asked to recover your Stalker account's password.",
          "your old password was reset and the new password associated to your Stalker account is:",
          "    %s",
          "Remember to change it to a new password on your next login in Stalker.");

  private void sendEmail(final String emailAddress, final String newPassword) {

    final String expandedBody = String.format(EMAIL_BODY_TEMPLATE, newPassword);

    final SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(EMAIL_SUBJECT);
    message.setTo(emailAddress);
    message.setText(expandedBody);

    log.info("email address: {}", Arrays.toString(message.getTo()));
    log.info("expanded email body: {}", message.getText());

    javaMailSender.send(message);
  }

  private static String hashPassword(final String password) {

    MessageDigest md;

    try {
      md = MessageDigest.getInstance("SHA-512");
    } catch (final NoSuchAlgorithmException e) {
      throw new UnsupportedOperationException("this should never happen");
    }

    final byte[] messageDigest = md.digest(password.getBytes());
    final StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < messageDigest.length; i++) {
      stringBuilder.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
    }

    return stringBuilder.toString();
  }

  // careful: not mockable
  private static final SecureRandom secureRandom = new SecureRandom();

  private String generateRandomSecurePassword() {
    final byte[] bytes = new byte[16];
    secureRandom.nextBytes(bytes);

    final Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    return encoder.encodeToString(bytes);
  }
}

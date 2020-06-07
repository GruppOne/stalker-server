package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.health.mail.enabled=false"})
class PasswordServiceTest {
  @MockBean UserRepository userRepository;
  @MockBean JavaMailSender javaMailSender;

  @Autowired PasswordService passwordService;

  @Test
  void testUpdatePassword() {
    // ARRANGE
    final long userId = 1L;
    final String email = "email@email.email";
    final String oldPassword = "oldPassword";
    final String newPassword = "newPassword";

    final UserDao oldUserDao =
        UserDao.builder().id(userId).email(email).password(oldPassword).build();
    final UserDao newUserDao =
        UserDao.builder().id(userId).email(email).password(newPassword).build();
    ;

    when(userRepository.findById(userId)).thenReturn(Mono.just(oldUserDao));
    when(userRepository.save(newUserDao)).thenReturn(Mono.just(newUserDao));

    // ACT
    final Mono<Void> sut = passwordService.updatePassword(oldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).verifyComplete();

    verify(userRepository).findById(userId);
    verify(userRepository).save(newUserDao);
  }

  @Test
  void testUpdatePasswordWrongOldPassword() {
    // ARRANGE
    final long userId = 1L;
    final String email = "email@email.email";
    final String wrongOldPassword = "wrongOldPassword";
    final String savedOldPassword = "savedOldPassword";
    final String newPassword = "newPassword";

    final UserDao oldUserDao =
        UserDao.builder().id(userId).email(email).password(savedOldPassword).build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(oldUserDao));

    // ACT
    final Mono<Void> sut = passwordService.updatePassword(wrongOldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).expectError(NotFoundException.class).verify();

    verify(userRepository).findById(userId);
  }

  @Test
  void testUpdatePasswordBlankNewPassword() {
    // ARRANGE
    final long userId = 1L;
    final String oldPassword = "oldPassword";
    final String newPassword = "";

    // ACT
    final Mono<Void> sut = passwordService.updatePassword(oldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).expectError(BadRequestException.class).verify();
  }

  @Test
  void testResetPassword() {
    final var userId = 1L;
    final var email = "email@email.email";
    final var oldPassword = "oldPassword";

    final var user = UserDao.builder().id(userId).email(email).password(oldPassword).build();

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
    when(userRepository.save(any())).thenReturn(Mono.just(user));

    ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

    passwordService.resetPassword(email);

    verify(userRepository).findByEmail(email);
    verify(userRepository).save(any());

    verify(javaMailSender).send(captor.capture());

    assertThat(captor.getValue().getTo()[0]).isEqualTo(email);
    assertThat(captor.getValue().getText()).contains("Hi,");
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.PasswordController.PostUserPasswordRecoveryRequestBody;
import tech.gruppone.stalker.server.controllers.PasswordController.PutUserByIdPasswordRequestBody;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// needed to create the javamailsender mockbean. ref: https://stackoverflow.com/a/41512008/13323696
@TestPropertySource(properties = {"management.health.mail.enabled=false"})
class PasswordControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean UserRepository userRepository;
  @MockBean JavaMailSender javaMailSender;

  @Test
  void testPutUserByIdPassword() {

    final long userId = 1L;
    final String email = "email@email.email";
    final String oldPassword = "oldPassword";
    final String newPassword = "newPassword";

    final PutUserByIdPasswordRequestBody updatePasswordDto =
        new PutUserByIdPasswordRequestBody(oldPassword, newPassword);

    final UserDao userDaoOldPassword =
        UserDao.builder().id(userId).email(email).password(oldPassword).build();
    final UserDao userDaoNewPassword =
        UserDao.builder().id(userId).email(email).password(newPassword).build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDaoOldPassword));
    when(userRepository.save(userDaoNewPassword)).thenReturn(Mono.just(userDaoNewPassword));

    webTestClient
        .put()
        .uri("/user/{userId}/password", userId)
        .body(Mono.just(updatePasswordDto), PutUserByIdPasswordRequestBody.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(userRepository).findById(userId);
    verify(userRepository).save(userDaoNewPassword);
  }

  @Test
  void testPostUserPasswordRecovery() {

    final var userId = 1L;
    final var email = "email@email.email";
    final var oldPassword = "oldPassword";

    final var user = UserDao.builder().id(userId).email(email).password(oldPassword).build();

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
    when(userRepository.save(any())).thenReturn(Mono.just(user));

    ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

    final PostUserPasswordRecoveryRequestBody requestBody =
        new PostUserPasswordRecoveryRequestBody(email);

    webTestClient
        .post()
        .uri("/user/password/recovery")
        .body(Mono.just(requestBody), PostUserPasswordRecoveryRequestBody.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(userRepository).findByEmail(email);
    verify(userRepository).save(any());

    verify(javaMailSender).send(captor.capture());

    assertThat(captor.getValue().getTo()[0]).isEqualTo(email);
    assertThat(captor.getValue().getText()).contains("Hi,");
  }
}

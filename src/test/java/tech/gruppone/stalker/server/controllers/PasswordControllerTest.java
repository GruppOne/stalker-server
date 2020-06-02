package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.PasswordController.PutUserByIdPasswordRequestBody;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PasswordControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean UserRepository userRepository;

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

    webTestClient
        .post()
        .uri("/user/password/recovery")
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

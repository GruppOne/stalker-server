package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.PasswordController.PutUserByIdPasswordRequestBody;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordControllerTest {

  @Autowired WebTestClient testClient;

  @MockBean UserService userService;
  @MockBean UserRepository userRepository;

  @Test
  public void testPutUserByIdPassword() {

    final long userId = 1L;
    final String email = "marioRossi@gmail.com";
    final String oldPassword = "mela";
    final String newPassword = "ciao";

    final PutUserByIdPasswordRequestBody updatePasswordDto =
        PutUserByIdPasswordRequestBody.builder()
            .oldPassword(oldPassword)
            .newPassword(newPassword)
            .build();

    final UserDao userDao1 =
        UserDao.builder().id(userId).email(email).password(newPassword).build();
    final UserDao userDao2 =
        UserDao.builder().id(userId).email(email).password(newPassword).build();

    doReturn(Mono.just(userDao2)).when(userRepository).save(userDao2);
    doReturn(Mono.just(userDao1)).when(userRepository).findById(userId);
    // when(userRepository.save(userDao)).thenReturn(userDao);

    testClient
        .put()
        .uri("/user/{userId}/password", userId)
        .body(Mono.just(updatePasswordDto), PutUserByIdPasswordRequestBody.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(userService).updatePassword(oldPassword, newPassword, userId);
  }
}

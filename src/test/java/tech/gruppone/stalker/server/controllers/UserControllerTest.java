package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UpdatePasswordDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

  @Autowired WebTestClient testClient;

  @MockBean UserService userService;

  @MockBean UserRepository userRepository;

  @Test
  public void testGetUser() {

    long userId = 1L;

    testClient.get().uri("/user/{userId}", userId).exchange().expectStatus().isOk();

    verify(userService).findById(userId);
  }

  @Test
  public void testDeleteUser() {

    long userId = 1L;

    testClient.delete().uri("/user/{userId}", userId).exchange().expectStatus().isNoContent();

    verify(userRepository).deleteUserById(userId);
  }

  @Test
  public void updatePassword() {

    long userId = 1L;

    UpdatePasswordDto updatePasswordDto =
        UpdatePasswordDto.builder().oldPassword("mela").newPassword("ciao").build();
    UserDao userDao1 =
        UserDao.builder().id(userId).email("marioRossi@gmail.com").password("ciao").build();
    UserDao userDao2 =
        UserDao.builder().id(userId).email("marioRossi@gmail.com").password("ciao").build();
    testClient
        .put()
        .uri("/user/{userId}/password", userId)
        .body(Mono.just(updatePasswordDto), UpdatePasswordDto.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    // when(userRepository.save(userDao)).thenReturn(userDao);

    doReturn(Mono.just(userDao2)).when(userRepository).save(userDao2);
    doReturn(Mono.just(userDao1)).when(userRepository).findById(userId);

    verify(userService).updatePassword(updatePasswordDto, userId);
  }
}

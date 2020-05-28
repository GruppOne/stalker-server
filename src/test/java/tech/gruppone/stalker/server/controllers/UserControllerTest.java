package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean
  UserService userService;

  @MockBean
  UserRepository userRepository;

  @MockBean
  UserDataRepository userDataRepository;

  @Test
  void testGetUserById() {

    final long userId = 1L;

    webTestClient.get().uri("/user/{userId}", userId).exchange().expectStatus().isOk();

    verify(userService).findById(userId);
  }

  @Test
  void testDeleteUserById() {

    final long userId = 1L;

    webTestClient.delete().uri("/user/{userId}", userId).exchange().expectStatus().isNoContent();

    verify(userRepository).deleteUserById(userId);
  }

  @Test
  public void testUpdatePassword() {

    long userId = 1L;

    UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder().oldPassword("mela").newPassword("ciao").build();
    UserDao userDao1 = UserDao.builder().id(userId).email("marioRossi@gmail.com").password("ciao").build();
    UserDao userDao2 = UserDao.builder().id(userId).email("marioRossi@gmail.com").password("ciao").build();
    webTestClient.put()
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

  @Test
  public void testPutUserById() {

    final Long userId = 1L;
    final String firstname = "Marco";
    final String lastname = "Rossi";
    final String email = "mariorossi@hotmail";
    final LocalDate birthdate = LocalDate.now();
    final LocalDateTime localDateTime = LocalDateTime.now();

    final UserDataDto userDataDto =
        UserDataDto.builder()
            .firstName(firstname)
            .lastName(lastname)
            .email(email)
            .birthDate(birthdate)
            .creationDateTime(Timestamp.valueOf(localDateTime))
            .build();

    final UserDao userDao =
        UserDao.builder().id(userId).email("mariorossi@hotmail.it").password("ciao").build();

    final UserDataDao userDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(firstname)
            .lastName(lastname)
            .birthDate(birthdate)
            .createdDate(localDateTime)
            .lastModifiedDate(localDateTime)
            .build();

    doReturn(Mono.just(userDao)).when(userRepository).findById(userId);
    doReturn(Mono.just(userDataDao)).when(userDataRepository).save(userDataDao);

    webTestClient.put()
      .uri("/user/{userId}", userId)
      .body(Mono.just(userDataDto), UserDataDto.class)
      .exchange()
      .expectStatus()
      .isNoContent();
  }

}

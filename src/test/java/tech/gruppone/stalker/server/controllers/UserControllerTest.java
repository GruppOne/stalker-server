package tech.gruppone.stalker.server.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");
  private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @Autowired WebTestClient webTestClient;

  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  @Test
  void testGetUserById() {

    final long userId = 1L;
    final String firstName = "firstName";
    final String lastName = "lastName";
    final String email = "email@email.email";
    final String password = "password";
    final LocalDate birthDate = LOCAL_DATE;
    final LocalDateTime localDateTime = LOCAL_DATETIME;

    final UserDao userDao = UserDao.builder().id(userId).email(email).password(password).build();
    final UserDataDao userDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthDate)
            .createdDate(localDateTime)
            .lastModifiedDate(localDateTime)
            .build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao));
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(userDataDao));

    webTestClient
        .get()
        .uri("/user/{userId}", userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(userId)
        .jsonPath("$.data.email")
        .isEqualTo(email)
        .jsonPath("$.data.firstName")
        .isEqualTo(firstName)
        .jsonPath("$.data.lastName")
        .isEqualTo(lastName)
        .jsonPath("$.data.birthDate")
        .isEqualTo(birthDate.toString());

    verify(userRepository).findById(userId);
    verify(userDataRepository).findById(userId);
  }

  @Test
  void testDeleteUserById() {

    final long userId = 1L;

    webTestClient.delete().uri("/user/{userId}", userId).exchange().expectStatus().isNoContent();

    verify(userRepository).deleteById(userId);
  }

  @Test
  void testPutUserById() {

    final Long userId = 1L;
    final String email = "email@email.email";
    final String password = "password";
    final String firstName = "firstName";
    final String lastName = "lastName";
    final LocalDate birthdate = LOCAL_DATE;
    final LocalDateTime genericLocalDateTime = LOCAL_DATETIME;

    final UserDataDto userDataDto =
        UserDataDto.builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .birthDate(birthdate)
            .creationDateTime(Timestamp.valueOf(genericLocalDateTime))
            .build();

    final UserDao userDao = UserDao.builder().id(userId).email(email).password(password).build();

    final UserDataDao userDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(genericLocalDateTime)
            .lastModifiedDate(genericLocalDateTime)
            .build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao));
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(userDataDao));
    // meh
    when(userDataRepository.save(any())).thenReturn(Mono.empty());

    webTestClient
        .put()
        .uri("/user/{userId}", userId)
        .body(Mono.just(userDataDto), UserDataDto.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(userRepository).findById(userId);
    // verify(userDataRepository).save(userDataDao);
    verify(userDataRepository).save(any());
  }
}

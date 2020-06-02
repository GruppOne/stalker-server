package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.UsersController.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.LoginService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {
  static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");
  static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @Autowired WebTestClient webTestClient;

  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;
  @MockBean LoginService loginService;

  @Test
  void testGetUsers() {

    final Long userId = 2L;
    final String email = "email@email.email";
    final String password = "password";
    final String firstName = "firstName";
    final String lastName = "lastName";

    final var userDao = UserDao.builder().id(userId).email(email).password(password).build();
    final var userDataDao =
        UserDataDao.builder()
            .userId(userId)
            .birthDate(LOCAL_DATE)
            .firstName(firstName)
            .lastName(lastName)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(userRepository.findAll()).thenReturn(Flux.just(userDao));
    when(userDataRepository.findAll()).thenReturn(Flux.just(userDataDao));

    webTestClient
        .get()
        .uri("/users")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.users")
        .isArray()
        .jsonPath("$.users[0].id")
        .isEqualTo(userId)
        .jsonPath("$.users[0].data.email")
        .isEqualTo(email)
        .jsonPath("$.users[0].data.firstName")
        .isEqualTo(firstName)
        .jsonPath("$.users[0].data.lastName")
        .isEqualTo(lastName);

    verify(userRepository).findAll();
    verify(userDataRepository).findAll();
  }

  @Test
  void testPostUsers() {

    final long newUserId = 10L;
    final String email = "mariorossi@hotmail.it";
    final String password =
        "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531";

    final String firstName = "firstName";
    final String lastName = "lastName";

    final var loginDataDto = new LoginDataDto(email, password);
    final var userDataDto =
        UserDataDto.builder()
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(LOCAL_DATE)
            .build();

    final var userWithLoginDataDto = new UserDataWithLoginData(loginDataDto, userDataDto);

    final var userDaoWithoutId = UserDao.builder().email(email).password(password).build();
    final var userDataDao =
        UserDataDao.builder()
            .userId(newUserId)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(LOCAL_DATE)
            .build();

    when(userRepository.save(userDaoWithoutId))
        .thenReturn(Mono.just(userDaoWithoutId.withId(newUserId)));
    when(userDataRepository.save(userDataDao)).thenReturn(Mono.just(userDataDao));

    final String jwt = "jwt-token";
    when(loginService.login(email, password)).thenReturn(Mono.just(jwt));

    webTestClient
        .post()
        .uri("/users")
        .body(Mono.just(userWithLoginDataDto), UserDataWithLoginData.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.jwt")
        .isEqualTo(jwt);

    verify(userRepository).save(userDaoWithoutId);
    verify(userDataRepository).save(userDataDao);
    verify(loginService).login(email, password);
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.JwtService;
import tech.gruppone.stalker.server.services.UserService;
import tech.gruppone.stalker.server.services.UsersService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersControllerTest {

  @Autowired private WebTestClient testClient;

  @Autowired private JwtService jwtService;

  @MockBean private UserService userService;
  @MockBean private UsersService usersService;

  @MockBean private UserRepository userRepository;
  @MockBean private UserDataRepository userDataRepository;

  @Test
  void testgetUsers() {

    Long id = 2L;
    String email = "mariorossi@hotmail.it";
    final var userDao = UserDao.builder().id(id).email(email).password("ciao").build();
    final var userDataDto =
        UserDataDto.builder()
            .email(email)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userDto = new UserDto(2L, userDataDto);

    when(userRepository.findAll()).thenReturn(Flux.just(userDao));
    when(userService.findAll()).thenReturn(Flux.just(userDto));

    testClient
        .get()
        .uri("/users")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.users")
        .isArray()
        .jsonPath("$.users[0].id")
        .isEqualTo(id)
        .jsonPath("$.users[0].data.email")
        .isEqualTo(email)
        .jsonPath("$.users[0].data.firstName")
        .isEqualTo("Mario")
        .jsonPath("$.users[0].data.lastName")
        .isEqualTo("Rossi");

    verify(userService).findAll();
  }

  @Test
  void testPostUsers() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmail.it")
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao =
        UserDataDao.builder()
            .userId(10L)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    final var userDao =
        UserDao.builder()
            .id(10L)
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();

    when(userRepository.save(userDao)).thenReturn(Mono.just(userDao));
    when(userDataRepository.save(userDataDao)).thenReturn(Mono.just(userDataDao));

    when(userRepository.findByEmail(loginDataDto.getEmail())).thenReturn(Mono.just(userDao));

    when(usersService.signUpUser(
            userWithLoginDataDto.getLoginData(), userWithLoginDataDto.getUserData()))
        .thenReturn(Mono.just(jwtService.createToken(10L)));

    testClient
        .post()
        .uri("/users")
        .body(Mono.just(userWithLoginDataDto), UserDataWithLoginData.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.jwt")
        .exists();

    verify(usersService)
        .signUpUser(userWithLoginDataDto.getLoginData(), userWithLoginDataDto.getUserData());
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.security.JwtConfiguration;
import tech.gruppone.stalker.server.services.LoginService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

  @Autowired WebTestClient testClient;
  @Autowired JwtConfiguration jwtConfiguration;

  @MockBean private UserRepository userRepository;
  @MockBean private LoginService loginService;

  @Test
  public void testLoginUser() {

    final var email = "mariotest01@gmail.com";
    final var password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final var loginData = new LoginDataDto(email, password);
    final var userDao = UserDao.builder().email(email).password(password).id(1L).build();

    // TODO this should use the other syntax: when(...).method(...)
    doReturn(Mono.just(userDao)).when(userRepository).findByEmail(loginData.getEmail());
    doReturn(Mono.just(jwtConfiguration.createToken(userDao.getId())))
        .when(loginService)
        .logUser(loginData);

    testClient
        .post()
        .uri("/user/login")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(loginData), LoginDataDto.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.jwt")
        .exists();

    verify(loginService).logUser(loginData);
  }
}

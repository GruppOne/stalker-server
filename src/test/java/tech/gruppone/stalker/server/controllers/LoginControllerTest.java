package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.JwtService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean UserRepository userRepository;
  @MockBean JwtService jwtService;

  @Test
  void testPostUserLogin() {

    final long userId = 1L;
    final var email = "mariotest01@gmail.com";
    final var password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final var userDao = UserDao.builder().id(userId).email(email).password(password).build();

    String fakeJwt = "not-really-a-jwt-but-good-enough";

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(userDao));
    when(jwtService.createToken(userId)).thenReturn(fakeJwt);

    final var loginData = new LoginDataDto(email, password);

    webTestClient
        .post()
        .uri("/user/login")
        .body(Mono.just(loginData), LoginDataDto.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.jwt")
        .isEqualTo(fakeJwt);

    verify(userRepository).findByEmail(email);
    verify(jwtService).createToken(userId);
  }

  @Test
  void testPostUserAnonymous() {

    when(jwtService.createAnonymousToken()).thenReturn("not.really.a-jwt");

    webTestClient
        .post()
        .uri("/user/anonymous")
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.anonymousJwt")
        .exists();
  }
}

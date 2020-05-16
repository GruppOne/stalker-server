package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.services.LoginService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class postUserLoginTest {

  @Autowired WebTestClient testClient;

  @MockBean LoginService loginService;

  @Test
  public void testLoginUser() {

    String email = "mariotest01@gmail.com";
    String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    LoginDataDto loginData = new LoginDataDto(email, password);

    testClient
        .post()
        .uri("/user/login")
        .body(Mono.just(loginData), LoginDataDto.class)
        .exchange()
        .expectStatus()
        .isCreated();

    verify(loginService).logUser(loginData);
  }
}

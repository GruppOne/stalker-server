package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  @MockBean UserRepository userRepository;
  @MockBean  LoginService loginService;

  @Test
  public void testLoginUser() {

    String email = "mariotest01@gmail.com";
    String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    LoginDataDto loginData = new LoginDataDto(email, password);
    UserDao userDao = UserDao.builder().email(email).password(password).id(1L).build();
    when(userRepository.findByEmail(email)).thenReturn(userDao);

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

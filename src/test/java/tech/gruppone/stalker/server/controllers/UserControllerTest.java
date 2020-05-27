package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean UserService userService;

  @MockBean UserRepository userRepository;

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
  void testPutUserById() {
    final long userId = 1L;

    webTestClient
        .put()
        .uri("/user/{userId}", userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

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
}

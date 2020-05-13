package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionControllerTests {

  @Autowired WebTestClient testClient;

  @MockBean ConnectionRepository connectionRepository;

  @Test
  public void testCreateUserConnection() {

    long userId = 1L;
    long organizationId = 1L;

    testClient
        .post()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .exchange()
        .expectStatus()
        .isCreated();

    // checks that the mock method has been called with the given parameters
    verify(connectionRepository).createUserConnection(userId, organizationId);
  }

  @Test
  public void testDeleteUserConnectionToOrganization() {
    long userId = 1L;
    long organizationId = 1L;

    testClient
        .delete()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .exchange()
        .expectStatus()
        .isNoContent();

    // checks that the mock method has been called with the given parameters
    verify(connectionRepository).deleteUserConnection(userId, organizationId);
  }
}

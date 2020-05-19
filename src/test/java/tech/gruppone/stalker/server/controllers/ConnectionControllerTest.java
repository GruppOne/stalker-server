package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionControllerTest {

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

  @Test
  public void testGetConnectedOrganizationsByUserId(){
    long userId = 1L;

    List<Long> listIds = new ArrayList<>();
    listIds.add(1L);
    listIds.add(2L);

    when(connectionRepository.findConnectedOrganizationsByUserId(userId)).thenReturn(Flux.fromIterable(listIds));

    testClient
        .get()
        .uri("/user/{userId}/organizations/connections", userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.connectedOrganizationsIds")
        .isArray();

    verify(connectionRepository).findConnectedOrganizationsByUserId(userId);
  }
}

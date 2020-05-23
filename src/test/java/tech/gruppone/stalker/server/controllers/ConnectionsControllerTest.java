package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionsControllerTest {

  @Autowired WebTestClient testClient;

  @MockBean ConnectionRepository connectionRepository;

  @Test
  void testGetUserByIdOrganizationsConnections() {
    long userId = 1L;

    List<Long> listIds = List.of(1L);

    when(connectionRepository.findConnectedOrganizationsByUserId(userId))
        .thenReturn(Flux.fromIterable(listIds));

    testClient
        .get()
        .uri("/user/{userId}/organizations/connections", userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.connectedOrganizationsIds")
        .isArray()
        .jsonPath("$.connectedOrganizationsIds[0]")
        .isEqualTo(1l);

    verify(connectionRepository).findConnectedOrganizationsByUserId(userId);
  }
}

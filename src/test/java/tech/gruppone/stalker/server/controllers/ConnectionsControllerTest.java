package tech.gruppone.stalker.server.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@Tag("slow")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionsControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean ConnectionRepository connectionRepository;

  @Test
  void testGetUserByIdOrganizationsConnections() {
    long userId = 1L;

    List<Long> listIds = List.of(1L);

    when(connectionRepository.findConnectedOrganizationsByUserId(userId))
        .thenReturn(Flux.fromIterable(listIds));

    webTestClient
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

  @Test
  void testGetOrganizationByIdUsersConnections() {

    assertTrue(false);
  }
}

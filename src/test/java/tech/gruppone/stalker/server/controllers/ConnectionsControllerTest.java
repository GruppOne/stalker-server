package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.services.ConnectionService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionsControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean ConnectionRepository connectionRepository;

  // it's easier to mock the service than the three underlying repositories
  @MockBean ConnectionService connectionService;

  @Test
  void testGetOrganizationByIdUsersConnections() {

    final long organizationId = 1L;

    final var userDto1 =
        new UserDto(
            1L,
            UserDataDto.builder()
                .email("email1@gmail.com")
                .firstName("firstname1")
                .lastName("lastName1")
                .birthDate(LocalDate.now())
                .creationDateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    final var userDto2 =
        new UserDto(
            2L,
            UserDataDto.builder()
                .email("email2@gmail.com")
                .firstName("firstname2")
                .lastName("lastName2")
                .birthDate(LocalDate.now())
                .creationDateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());

    when(connectionService.findConnectedUsersByOrganizationId(organizationId))
        .thenReturn(Flux.just(userDto1, userDto2));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/connections", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.connectedUsers")
        .isArray()
        .jsonPath("$.connectedUsers[0].id")
        .isEqualTo(userDto1.getId())
        .jsonPath("$.connectedUsers[1].data.lastName")
        .isEqualTo(userDto2.getData().getLastName());

    verify(connectionService).findConnectedUsersByOrganizationId(organizationId);
  }

  @Test
  void testGetUserByIdOrganizationsConnections() {
    final long userId = 1L;

    final var organizationIds = List.of(1L, 2L);

    when(connectionRepository.findConnectedOrganizationIdsByUserId(userId))
        .thenReturn(Flux.fromIterable(organizationIds));

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
        .isEqualTo(organizationIds.get(0))
        .jsonPath("$.connectedOrganizationsIds[1]")
        .isEqualTo(organizationIds.get(1));

    verify(connectionRepository).findConnectedOrganizationIdsByUserId(userId);
  }
}

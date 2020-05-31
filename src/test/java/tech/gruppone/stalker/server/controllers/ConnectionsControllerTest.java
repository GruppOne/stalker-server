package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import tech.gruppone.stalker.server.services.OrganizationService;ories.ConnectionRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionsControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean ConnectionRepository connectionRepository;

  @Test
  void testGetOrganizationByIdUsersConnections() {

    final long organizationId = 1L;

    // webTestClient
    //     .get()
    //     .uri("/organization/{organizationId}/users/connections", organizationId)
    //     .exchange()
    //     .expectStatus()
    //     .isEqualTo(HttpStatus.NOT_IMPLEMENTED);

    var userDto1 =
        new UserDto(
            1L,
            UserDataDto.builder()
                .email("email1@gmail.com")
                .firstName("firstname1")
                .lastName("lastName1")
                .birthDate(LocalDate.now())
                .creationDateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    var userDto2 =
        new UserDto(
            2L,
            UserDataDto.builder()
                .email("email2@gmail.com")
                .firstName("firstname2")
                .lastName("lastName2")
                .birthDate(LocalDate.now())
                .creationDateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build());

    List<UserDto> connectedUsers = new ArrayList<>();
    connectedUsers.add(userDto1);
    connectedUsers.add(userDto2);

    when(organizationService.findConnectedUsersByOrganizationId(organizationId))
        .thenReturn(Flux.fromIterable(connectedUsers));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/connections", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.connectedUsers")
        .isArray();

    verify(organizationService).findConnectedUsersByOrganizationId(organizationId);
  }

  @Test
  void testGetUserByIdOrganizationsConnections() {
    final long userId = 1L;

    final List<Long> listIds = List.of(1L);

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
        .isEqualTo(1L);

    verify(connectionRepository).findConnectedOrganizationsByUserId(userId);
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto.PlaceHistoryDto;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoryControllerTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(1577840461);

  @MockBean ConnectionRepository connectionRepository;
  @MockBean LocationInfoRepository locationInfoRepository;

  @Autowired WebTestClient webTestClient;

  @Test
  void testGetOrganizationByIdUserByIdHistory() {

    final long organizationId = 1L;
    final long userId = 1L;

    final long placeId = 11L;
    final boolean inside = true;

    final var placeHistoryDto =
        PlaceHistoryDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .inside(inside)
            .placeId(placeId)
            .build();

    when(locationInfoRepository.findHistoryByOrganizationIdAndUserId(organizationId, userId))
        .thenReturn(Flux.just(placeHistoryDto));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/user/{userId}/history", organizationId, userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.userId")
        .isEqualTo(userId)
        .jsonPath("$.history[0].placeId")
        .isEqualTo(placeId)
        .jsonPath("$.history[0].inside")
        .isEqualTo(inside);

    verify(locationInfoRepository).findHistoryByOrganizationIdAndUserId(organizationId, userId);
  }

  @Test
  void testGetUserByIdHistory() {

    final long userId = 1L;

    final long organizationId = 1L;
    final long placeId = 11L;
    final boolean inside = true;

    final var placeHistoryDto =
        PlaceHistoryDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .inside(inside)
            .placeId(placeId)
            .build();

    when(connectionRepository.findConnectedOrganizationIdsByUserId(userId))
        .thenReturn(Flux.just(organizationId));

    when(locationInfoRepository.findHistoryByOrganizationIdAndUserId(organizationId, userId))
        .thenReturn(Flux.just(placeHistoryDto));

    webTestClient
        .get()
        .uri("/user/{userId}/history", userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.history[0].organizationId")
        .isEqualTo(organizationId)
        .jsonPath("$.history[0].historyPerOrganization.history[0].placeId")
        .isEqualTo(placeId);

    verify(connectionRepository).findConnectedOrganizationIdsByUserId(userId);
    verify(locationInfoRepository).findHistoryByOrganizationIdAndUserId(organizationId, userId);
  }
}

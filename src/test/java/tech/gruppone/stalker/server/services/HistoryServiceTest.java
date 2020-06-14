package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.UserHistoryDto;
import tech.gruppone.stalker.server.model.api.UserHistoryDto.OrganizationHistoryDto;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto.PlaceHistoryDto;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = HistoryService.class)
class HistoryServiceTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(1577840461);

  @MockBean ConnectionRepository connectionRepository;
  @MockBean LocationInfoRepository locationInfoRepository;

  @Autowired HistoryService historyService;

  final Long userId = 1L;
  final Long organizationId = 11L;

  @Test
  void testFindHistoryByOrganizationIdAndUserId() {
    final var placeId = 111L;
    final var inside = true;

    final var placeHistoryDto =
        PlaceHistoryDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .inside(inside)
            .placeId(placeId)
            .build();

    when(locationInfoRepository.findHistoryByOrganizationIdAndUserId(organizationId, userId))
        .thenReturn(Flux.just(placeHistoryDto));

    final UserHistoryPerOrganizationDto expectedUserHistoryPerOrganizationDto =
        new UserHistoryPerOrganizationDto(userId, List.of(placeHistoryDto));

    final var sut = historyService.findHistoryByOrganizationIdAndUserId(organizationId, userId);

    StepVerifier.create(sut).expectNext(expectedUserHistoryPerOrganizationDto).verifyComplete();

    verify(locationInfoRepository).findHistoryByOrganizationIdAndUserId(organizationId, userId);
  }

  @Test
  void testFindHistoryByUserId() {
    final var placeId = 111L;
    final var inside = false;

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

    final var userHistoryPerOrganization =
        new UserHistoryPerOrganizationDto(userId, List.of(placeHistoryDto));
    final var organizationHistory =
        new OrganizationHistoryDto(organizationId, userHistoryPerOrganization);

    final var expectedUserHistory = new UserHistoryDto(List.of(organizationHistory));

    final var sut = historyService.findHistoryByUserId(userId);

    StepVerifier.create(sut).expectNext(expectedUserHistory).verifyComplete();

    verify(connectionRepository).findConnectedOrganizationIdsByUserId(userId);
    verify(locationInfoRepository).findHistoryByOrganizationIdAndUserId(organizationId, userId);
  }
}

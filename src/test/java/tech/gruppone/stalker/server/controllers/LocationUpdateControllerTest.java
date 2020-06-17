package tech.gruppone.stalker.server.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LocationUpdateControllerTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(1577840461);

  @Autowired WebTestClient webTestClient;

  @MockBean LocationInfoRepository locationinfoRepository;
  @MockBean PlaceRepository placeRepository;

  @Test
  void testPostLocationUpdate() {

    final Long placeId = 1L;
    final Long organizationId = 1L;
    final String placeName = "placeName";
    final String color = "color";
    final String address = "address";
    final String city = "city";
    final String zipcode = "zipcode";
    final String state = "state";
    final List<Long> placeIds = List.of(placeId);

    final var place =
        PlaceDao.builder()
            .id(placeId)
            .organizationId(organizationId)
            .name(placeName)
            .color(color)
            .maxConcurrentUsers(111)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    when(placeRepository.findAllById(placeIds)).thenReturn(Flux.just(place));

    final String userId = "1";
    final var inside = true;

    final MultiLocationInfoDto multiLocationInfo =
        MultiLocationInfoDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .userType("known")
            .userId(userId)
            .inside(inside)
            .placeIds(placeIds)
            .build();
    final var anonymous = multiLocationInfo.isAnonymous();

    when(locationinfoRepository.findLastStatusByUserIdAndPlaceId(userId, String.valueOf(placeId)))
        .thenReturn(Mono.just(inside));

    final ArgumentCaptor<LocationInfo> captor = ArgumentCaptor.forClass(LocationInfo.class);

    webTestClient
        .post()
        .uri("/location/update")
        .body(Mono.just(multiLocationInfo), MultiLocationInfoDto.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .isEmpty();

    verify(placeRepository).findAllById(placeIds);
    verify(locationinfoRepository, times(placeIds.size())).save(captor.capture());
    verify(locationinfoRepository, times(0)).saveWithInfiniteRp(any());

    captor
        .getAllValues()
        .forEach(
            locationInfo -> {
              assertThat(locationInfo.getTime()).isEqualTo(INSTANT);
              assertThat(locationInfo.getUserId()).isEqualTo(userId);
              assertThat(locationInfo.getAnonymous()).isEqualTo(anonymous);
              assertThat(locationInfo.getInside()).isEqualTo(inside);
              assertThat(locationInfo.getOrganizationId())
                  .isEqualTo(String.valueOf(organizationId));
            });

    // shouldnt modify the actual values tested, only the expectations...
    assertThat(captor.getAllValues().stream().map(LocationInfo::getPlaceId).map(Long::valueOf))
        .containsAll(placeIds);
  }
}

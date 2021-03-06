package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.model.api.UsersInsideOrganizationDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = LocationService.class)
class LocationServiceTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(1577840461);

  @MockBean LocationInfoRepository locationInfoRepository;
  @MockBean PlaceRepository placeRepository;

  @Autowired LocationService locationService;

  // set some common place variables
  final Long placeId1 = 1L;
  final Long placeId2 = 2L;
  final Long organizationId = 1L;
  final String placeName = "placeName";
  final String color = "color";
  final String address = "address";
  final String city = "city";
  final String zipcode = "zipcode";
  final String state = "state";

  final List<Long> placeIds = List.of(placeId1, placeId2);

  @Test
  void testSaveMulti() {

    final var placeBuilder =
        PlaceDao.builder()
            .organizationId(organizationId)
            .name(placeName)
            .color(color)
            .maxConcurrentUsers(111)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state);

    final var place1 = placeBuilder.id(placeId1).build();
    final var place2 = placeBuilder.id(placeId2).build();

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

    when(placeRepository.findAllById(placeIds)).thenReturn(Flux.just(place1, place2));

    when(locationInfoRepository.findLastStatusByUserIdAndPlaceId(userId, String.valueOf(placeId1)))
        .thenReturn(Mono.just(!inside));
    when(locationInfoRepository.findLastStatusByUserIdAndPlaceId(userId, String.valueOf(placeId2)))
        .thenReturn(Mono.just(inside));

    ArgumentCaptor<LocationInfo> saveCaptor = ArgumentCaptor.forClass(LocationInfo.class);
    ArgumentCaptor<LocationInfo> saveInfiniteCaptor = ArgumentCaptor.forClass(LocationInfo.class);

    final var sut = locationService.saveMulti(multiLocationInfo);

    StepVerifier.create(sut).verifyComplete();

    verify(placeRepository).findAllById(placeIds);
    verify(locationInfoRepository, times(placeIds.size())).save(saveCaptor.capture());
    verify(locationInfoRepository).saveWithInfiniteRp(saveInfiniteCaptor.capture());

    saveCaptor
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
    assertThat(saveCaptor.getAllValues().stream().map(LocationInfo::getPlaceId).map(Long::valueOf))
        .containsAll(placeIds);

    final var saveInfiniteValues = saveInfiniteCaptor.getAllValues();

    assertThat(saveInfiniteValues.size()).isEqualTo(1);
    assertThat(saveInfiniteValues.get(0).getPlaceId()).isEqualTo(String.valueOf(placeId1));
  }

  @Test
  void testCountUsersCurrentlyInsideOrganizationById() {
    final var organizationId = 1L;
    final var placeId = 11L;
    final var usersInside = 1;
    final var expectedDto =
        new UsersInsideOrganizationDto(
            List.of(new UsersInsideOrganizationDto.UsersInsidePlaceDto(placeId, usersInside)));

    when(locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationId))
        .thenReturn(Flux.just(Tuples.of(placeId, usersInside)));

    final var sut = locationService.countUsersCurrentlyInsideOrganizationById(organizationId);

    StepVerifier.create(sut).expectNext(expectedDto).verifyComplete();

    verify(locationInfoRepository).findByOrganizationIdGroupByPlaceId(organizationId);
  }

  @Test
  void testCountUsersCurrentlyInsideOrganizationByIdWhenNoOneInside() {
    final var organizationId = 1L;

    final var expectedDto = new UsersInsideOrganizationDto(List.of());

    when(locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationId))
        .thenReturn(Flux.empty());

    final var sut = locationService.countUsersCurrentlyInsideOrganizationById(organizationId);

    StepVerifier.create(sut).expectNext(expectedDto).verifyComplete();

    verify(locationInfoRepository).findByOrganizationIdGroupByPlaceId(organizationId);
  }
}

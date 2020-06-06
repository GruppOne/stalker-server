package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

  @Mock OrganizationRepository organizationRepository;
  @Mock PlaceRepository placeRepository;
  @Mock PlacePositionService placePositionService;

  @InjectMocks PlaceService placeService;

  // Set common variables
  final Long placeId = 1L;
  final Long organizationId = 11L;
  final String name = "name";
  final String color = "#ffffff";
  final Integer maxConcurrentUsers = 111;
  final String polygonJson = "{\"zzz\":false}";
  final List<GeographicalPoint> polygonList = Collections.emptyList();
  final String address = "address";
  final String city = "city";
  final String zipcode = "zipcode";
  final String state = "state";

  @Test
  void testFindById() {
    final var placeDao =
        PlaceDao.builder()
            .id(placeId)
            .organizationId(organizationId)
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    final var expectedPlaceInfo =
        PlaceInfo.builder().address(address).city(city).zipcode(zipcode).state(state).build();
    final var expectedPlaceDataDto =
        PlaceDataDto.builder()
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .polygon(polygonList)
            .placeInfo(expectedPlaceInfo)
            .build();
    final var expectedPlaceDto = new PlaceDto(placeId, expectedPlaceDataDto);

    when(placeRepository.findById(placeId)).thenReturn(Mono.just(placeDao));
    when(placePositionService.findGeographicalPointsByPlaceId(placeId))
        .thenReturn(Mono.just(polygonList));

    final var sut = placeService.findById(placeId);

    StepVerifier.create(sut).expectNext(expectedPlaceDto).verifyComplete();

    verify(placeRepository).findById(placeId);
    verify(placePositionService).findGeographicalPointsByPlaceId(placeId);
  }

  @Test
  void testFindAllByOrganizationId() {
    final var placeDao =
        PlaceDao.builder()
            .id(placeId)
            .organizationId(organizationId)
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    final var expectedPlaceInfo =
        PlaceInfo.builder().address(address).city(city).zipcode(zipcode).state(state).build();
    final var expectedPlaceDataDto =
        PlaceDataDto.builder()
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .polygon(polygonList)
            .placeInfo(expectedPlaceInfo)
            .build();
    final var expectedPlaceDto = new PlaceDto(placeId, expectedPlaceDataDto);

    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(placeDao));
    when(placePositionService.findGeographicalPointsByPlaceId(placeId))
        .thenReturn(Mono.just(polygonList));

    final var sut = placeService.findAllByOrganizationId(organizationId);

    StepVerifier.create(sut).expectNext(expectedPlaceDto).verifyComplete();

    verify(placeRepository).findAllByOrganizationId(organizationId);
    verify(placePositionService).findGeographicalPointsByPlaceId(placeId);
  }

  @Test
  void testSave() {
    final var placeInfo =
        PlaceInfo.builder().address(address).city(city).zipcode(zipcode).state(state).build();
    final var placeDataDto =
        PlaceDataDto.builder()
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .polygon(polygonList)
            .placeInfo(placeInfo)
            .build();

    final Long newPlaceId = 111L;

    final var placeDao =
        PlaceDao.builder()
            .organizationId(organizationId)
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    when(placeRepository.save(placeDao)).thenReturn(Mono.just(placeDao.withId(newPlaceId)));
    when(placePositionService.save(newPlaceId, polygonList)).thenReturn(Mono.just(1));

    // meaningless
    when(organizationRepository.findById(organizationId)).thenReturn(Mono.empty());

    final var sut = placeService.save(organizationId, placeDataDto);

    StepVerifier.create(sut).expectNext(newPlaceId).verifyComplete();

    verify(placeRepository).save(placeDao);
    verify(placePositionService).save(newPlaceId, polygonList);
    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testUpdateById() {
    final var placeInfo =
        PlaceInfo.builder().address(address).city(city).zipcode(zipcode).state(state).build();
    final var placeDataDto =
        PlaceDataDto.builder()
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .polygon(polygonList)
            .placeInfo(placeInfo)
            .build();

    final Long placeId = 111L;

    final var placeDao =
        PlaceDao.builder()
            .id(placeId)
            .organizationId(organizationId)
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    when(placeRepository.save(placeDao)).thenReturn(Mono.just(placeDao));
    when(placePositionService.save(placeId, polygonList)).thenReturn(Mono.just(1));

    // meaningless
    when(organizationRepository.findById(organizationId)).thenReturn(Mono.empty());

    final var sut = placeService.updateById(placeId, organizationId, placeDataDto);

    StepVerifier.create(sut).verifyComplete();

    verify(placeRepository).save(placeDao);
    verify(placePositionService).save(placeId, polygonList);
    verify(organizationRepository).findById(organizationId);
  }
}

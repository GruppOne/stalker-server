package tech.gruppone.stalker.server.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
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
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

  @Mock private PlacePositionService placePositionService;

  @Mock private PlaceRepository placeRepository;
  @InjectMocks private PlaceService placeService;

  // Should find a cleaner way to set these wariables across tests...
  long id = 1L;
  long organizationId = 11L;
  String name = "name";
  String polygonJson = "{\"zzz\":false}";
  List<GeographicalPoint> polygonList = Collections.emptyList();
  String address = "address";
  String city = "city";
  String zipcode = "zipcode";
  String state = "state";
  PlaceDao placeDao =
      PlaceDao.builder()
          .id(id)
          .organizationId(organizationId)
          .name(name)
          .address(address)
          .city(city)
          .zipcode(zipcode)
          .state(state)
          .build();
  PlaceDto expectedPlaceDto =
      new PlaceDto(
          id,
          PlaceDataDto.builder()
              .name(name)
              .polygon(polygonList)
              .placeInfo(
                  PlaceInfo.builder()
                      .address(address)
                      .city(city)
                      .zipcode(zipcode)
                      .state(state)
                      .build())
              .build());

  @Test
  void testFindById() {
    when(placeRepository.findById(id)).thenReturn(Mono.just(placeDao));

    var sut = placeService.findById(id);

    sut.as(StepVerifier::create).expectNext(expectedPlaceDto);
  }

  @Test
  void testFindAllByOrganizationId() {
    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(placeDao));

    var sut = placeService.findAllByOrganizationId(organizationId);

    sut.as(StepVerifier::create).expectNext(expectedPlaceDto);
  }

  @Test
  void testSaveAll() {
    PlaceDao expectedPlaceDao =
        PlaceDao.builder()
            .id(1L)
            .organizationId(1L)
            .name(name)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    PlaceDao newPlaceDao =
        PlaceDao.builder()
            .organizationId(1L)
            .name(name)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    when(placeRepository.save(newPlaceDao)).thenReturn(Mono.just(expectedPlaceDao));
    when(placePositionService.savePlacePosition(eq(id), any())).thenReturn(Mono.just(1));

    var placeDataDtos = Flux.just(expectedPlaceDto).map(PlaceDto::getData);

    Flux<PlaceDao> sut = placeService.saveAll(placeDataDtos, 1L);

    sut.as(StepVerifier::create).expectNext(expectedPlaceDao).verifyComplete();

    // FIXME these should not be invoked twice!
    verify(placeRepository, times(2)).save(newPlaceDao);
    verify(placePositionService, times(2)).savePlacePosition(eq(id), any());
  }
}

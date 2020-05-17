package tech.gruppone.stalker.server.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.PlacePositionRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@SpringBootTest
class PlaceServiceTest {

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

  @MockBean private PlaceRepository placeRepository;
  @Autowired private PlaceService placeService;

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

  @MockBean private PlacePositionRepository placePositionRepository;

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
    when(placePositionRepository.create(eq(id), any())).thenReturn(Mono.just(1));

    var placeDataDtos = Flux.just(expectedPlaceDto).map(PlaceDto::getData);

    Flux<PlaceDao> sut = placeService.saveAll(placeDataDtos, 1L);

    sut.as(StepVerifier::create).expectNext(expectedPlaceDao);
  }

  // this test was kind of meaningless...
  // void testSaveAll() {
  //   PlaceDao newPlaceDao =
  //       PlaceDao.builder()
  //           .organizationId(organizationId)
  //           .name(name)
  //           .address(address)
  //           .city(city)
  //           .zipcode(zipcode)
  //           .state(state)
  //           .build();

  //   when(placeRepository.saveAll(List.of(newPlaceDao))).thenReturn(Flux.just(placeDao));

  //   Flux<PlaceDataDto> placeDataDtos = Flux.empty();
  //   var sut = placeService.saveAll(placeDataDtos, 1L);

  //   sut.as(StepVerifier::create).expectComplete();
  // }
}

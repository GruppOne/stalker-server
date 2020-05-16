package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
      new PlaceDao(id, organizationId, name, polygonJson, address, city, zipcode, state);
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
  @MockBean private PlaceSerializationService positionService;
  @Autowired private PlaceService placeService;

  @Test
  void testFindById() {
    when(placeRepository.findById(id)).thenReturn(Mono.just(placeDao));

    var sut = placeService.findById(id);

    assertThat(sut.block()).isEqualTo(expectedPlaceDto);
  }

  @Test
  void testFindAllByOrganizationId() {
    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(placeDao));

    var sut = placeService.findAllByOrganizationId(organizationId);

    assertThat(sut.next().block()).isEqualTo(expectedPlaceDto);
  }

  @Test
  // TODO this test is kind of meaningless...
  void testSaveAll() {
    when(placeRepository.create(any(), any(), any())).thenReturn(Mono.empty());

    List<PlaceDto> placeDtos = Collections.emptyList();
    var sut = placeService.saveAll(placeDtos);

    StepVerifier.create(sut).expectComplete();
  }
}

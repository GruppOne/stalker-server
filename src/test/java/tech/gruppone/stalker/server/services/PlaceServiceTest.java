package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
  @MockBean private PositionService positionService;
  @Autowired private PlaceService placeService;

  @Test
  void testFindById() {
    doReturn(Mono.just(placeDao)).when(placeRepository).findById(id);

    var sut = placeService.findById(id);

    assertThat(sut.block()).isEqualTo(expectedPlaceDto);
  }

  @Test
  void testFindAllByOrganizationId() {
    doReturn(Flux.just(placeDao)).when(placeRepository).findAllByOrganizationId(organizationId);

    var sut = placeService.findAllByOrganizationId(organizationId);

    assertThat(sut.next().block()).isEqualTo(expectedPlaceDto);
  }
}

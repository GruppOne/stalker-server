package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;
import tech.gruppone.stalker.server.services.PlacePositionService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaceControllerTest {
  @Autowired WebTestClient webTestClient;

  @MockBean OrganizationRepository organizationRepository;
  @MockBean PlaceRepository placeRepository;
  @MockBean PlacePositionService placePositionService;

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
  void testDeleteOrganizationByIdPlaceById() {

    when(placeRepository.deleteById(placeId)).thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(placeRepository).deleteById(placeId);
  }

  @Test
  void testGetOrganizationByIdPlaceById() {
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

    when(placeRepository.findById(placeId)).thenReturn(Mono.just(placeDao));
    when(placePositionService.findGeographicalPointsByPlaceId(placeId))
        .thenReturn(Mono.just(polygonList));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(placeId)
        .jsonPath("$.data.name")
        .isEqualTo(name)
        .jsonPath("$.data.color")
        .isEqualTo(color)
        .jsonPath("$.data.maxConcurrentUsers")
        .isEqualTo(maxConcurrentUsers)
        .jsonPath("$.data.placeInfo.address")
        .isEqualTo(address)
        .jsonPath("$.data.placeInfo.zipcode")
        .isEqualTo(zipcode);

    verify(placeRepository).findById(placeId);
    verify(placePositionService).findGeographicalPointsByPlaceId(placeId);
  }

  @Test
  void testPutOrganizationByIdPlaceById() {
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

    final var placeDto = new PlaceDto(placeId, placeDataDto);

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

    webTestClient
        .put()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .body(Mono.just(placeDto), PlaceDto.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(placeRepository).save(placeDao);
    verify(placePositionService).save(placeId, polygonList);
    verify(organizationRepository).findById(organizationId);
  }
}

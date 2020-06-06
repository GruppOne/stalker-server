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
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;
import tech.gruppone.stalker.server.services.PlacePositionService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlacesControllerTest {
  @Autowired WebTestClient webTestClient;

  @MockBean PlaceRepository placeRepository;
  @MockBean OrganizationRepository organizationRepository;
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
  void testGetOrganizationByIdPlaces() {
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

    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(placeDao));
    when(placePositionService.findGeographicalPointsByPlaceId(placeId))
        .thenReturn(Mono.just(polygonList));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/places", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.places")
        .isArray()
        .jsonPath("$.places[0].id")
        .isEqualTo(placeId)
        .jsonPath("$.places[0].data.name")
        .isEqualTo(name)
        .jsonPath("$.places[0].data.color")
        .isEqualTo(color)
        .jsonPath("$.places[0].data.maxConcurrentUsers")
        .isEqualTo(maxConcurrentUsers)
        .jsonPath("$.places[0].data.placeInfo.address")
        .isEqualTo(address)
        .jsonPath("$.places[0].data.placeInfo.zipcode")
        .isEqualTo(zipcode);

    verify(placeRepository).findAllByOrganizationId(organizationId);
    verify(placePositionService).findGeographicalPointsByPlaceId(placeId);
  }

  @Test
  void testPostOrganizationByIdPlaces() {
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

    webTestClient
        .post()
        .uri("/organization/{organizationId}/places", organizationId)
        .body(Mono.just(placeDataDto), PlaceDataDto.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(newPlaceId);

    verify(placeRepository).save(placeDao);
    verify(placePositionService).save(newPlaceId, polygonList);
    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testGetOrganizationByIdPlacesReport() {
    final long organizationId = 1L;

    webTestClient
        .get()
        .uri("/organization/{organizationId}/places/report", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

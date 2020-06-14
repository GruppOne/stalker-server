package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Tag("integrationTest")
@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationControllerTest {

  static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Autowired WebTestClient webTestClient;

  @MockBean OrganizationRepository organizationRepository;
  @MockBean PlaceRepository placeRepository;

  @MockBean LocationInfoRepository locationInfoRepository;

  // set some common variables
  final long organizationId = 1L;

  // place related variables
  final Long placeId = 11L;
  final String placeName = "placeName";
  final String color = "color";
  final String address = "address";
  final String city = "city";
  final String zipcode = "zipcode";
  final String state = "state";
  final PlaceDao place =
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
  final List<Long> placeIds = List.of(placeId);

  @Test
  void testGetOrganizationById() {

    final var name = "name";
    final var description = "description";

    final var organizationDao =
        OrganizationDao.builder()
            .id(organizationId)
            .name(name)
            .description(description)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organizationDao));
    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(place));

    webTestClient
        .get()
        .uri("/organization/{organizationId}", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(organizationId)
        .jsonPath("$.data.name")
        .isEqualTo(name)
        .jsonPath("$.data.description")
        .isEqualTo(description)
        .jsonPath("$.placeIds")
        .isArray()
        .jsonPath("$.placeIds[0]")
        .isEqualTo(placeId)
        .jsonPath("$.placeIds[1]")
        .doesNotExist();

    verify(organizationRepository).findById(organizationId);
    verify(placeRepository).findAllByOrganizationId(organizationId);
  }

  @Test
  void testPutOrganizationById() {
    final var newName = "newName";
    final var newDescription = "newDescription";
    final var organizationType = "public";

    final var organizationDataDto =
        OrganizationDataDto.builder()
            .name(newName)
            .description(newDescription)
            .organizationType(organizationType)
            .creationDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .lastChangeDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .build();

    final var organizationDto =
        OrganizationDto.builder()
            .id(organizationId)
            .data(organizationDataDto)
            .placeIds(List.of())
            .build();

    final var updatedOrganization =
        OrganizationDao.builder()
            .id(organizationId)
            .name(newName)
            .description(newDescription)
            .organizationType(organizationType)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME)
            .build();

    when(organizationRepository.save(updatedOrganization))
        .thenReturn(Mono.just(updatedOrganization));

    webTestClient
        .put()
        .uri("/organization/{organizationId}", organizationId)
        .body(Mono.just(organizationDto), OrganizationDto.class)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(organizationRepository).save(updatedOrganization);
  }

  @Test
  void testDeleteOrganizationById() {
    when(organizationRepository.deleteById(organizationId)).thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri("/organization/{organizationId}", organizationId)
        .exchange()
        .expectStatus()
        .isNoContent();

    verify(organizationRepository).deleteById(organizationId);
  }

  @Test
  void testGetOrganizationByIdUsersInside() {

    final var usersInside = 1;
    when(locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationId))
        .thenReturn(Flux.just(Tuples.of(placeId, usersInside)));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/inside", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.usersInside")
        .isEqualTo(usersInside)
        .jsonPath("$.places[0].placeId")
        .isEqualTo(placeId)
        .jsonPath("$.places[0].usersInside")
        .isEqualTo(usersInside);

    verify(locationInfoRepository).findByOrganizationIdGroupByPlaceId(organizationId);
  }
}

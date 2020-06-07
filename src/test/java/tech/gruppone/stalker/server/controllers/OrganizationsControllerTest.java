package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Tag("integrationTest")
@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationsControllerTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @MockBean OrganizationRepository organizationRepository;
  @MockBean PlaceRepository placeRepository;

  @Autowired WebTestClient webTestClient;

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
  void testGetOrganizations() {
    final var organizationId = 1L;
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

    when(organizationRepository.findAll()).thenReturn(Flux.just(organizationDao));
    when(placeRepository.findAll()).thenReturn(Flux.just(place));

    webTestClient
        .get()
        .uri("/organizations")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.organizations[0].id")
        .isEqualTo(organizationId)
        .jsonPath("$.organizations[0].data.name")
        .isEqualTo(name)
        .jsonPath("$.organizations[0].placeIds")
        .isArray();

    verify(organizationRepository).findAll();
    verify(placeRepository).findAll();
  }

  @Test
  void testPostOrganizations() {
    final String name = "name";
    final String description = "description";

    final OrganizationDataDto organizationDataDto =
        OrganizationDataDto.builder().name(name).description(description).build();

    final OrganizationDao expectedOrganizationDao =
        OrganizationDao.builder().id(organizationId).name(name).description(description).build();

    when(organizationRepository.save(expectedOrganizationDao.withId(null)))
        .thenReturn(Mono.just(expectedOrganizationDao));

    webTestClient
        .post()
        .uri("/organizations")
        .body(Mono.just(organizationDataDto), OrganizationDataDto.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(organizationId);

    verify(organizationRepository).save(expectedOrganizationDao.withId(null));
  }
}

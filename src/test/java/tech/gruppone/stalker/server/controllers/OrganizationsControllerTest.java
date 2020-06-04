package tech.gruppone.stalker.server.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.services.PlaceService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationsControllerTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @MockBean private OrganizationRepository organizationRepository;
  @MockBean private PlaceService placeService;

  @Autowired private WebTestClient webTestClient;

  @Test
  void testGetOrganizations() {
    final var organizationId = 1L;
    final var name = "name";
    final var description = "description";
    var isPrivate = 0L;

    final var organizationDao =
        OrganizationDao.builder()
            .id(organizationId)
            .name(name)
            .description(description)
            .isPrivate(isPrivate)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(organizationRepository.findAll()).thenReturn(Flux.just(organizationDao));
    when(organizationRepository.findById(1L)).thenReturn(Mono.just(organizationDao));
    when(placeService.findAllByOrganizationId(any(Long.class))).thenReturn(Flux.empty());

    webTestClient
        .get()
        .uri("/organizations")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.organizations[0].id")
        .isEqualTo(1L)
        .jsonPath("$.organizations[0].data.places")
        .isArray();

    verify(organizationRepository).findAll();
    verify(placeService).findAllByOrganizationId(1L);
  }

  @Test
  void testPostOrganizations() {
    final long organizationId = 1L;
    final String name = "name";
    final String description = "description";
    final Long isPrivate = 0L;

    final OrganizationDataDto organizationDataDto =
        OrganizationDataDto.builder()
            .name(name)
            .description(description)
            .isPrivate(isPrivate)
            .build();

    final OrganizationDao expectedOrganizationDao =
        OrganizationDao.builder().id(organizationId).name(name).description(description).build();

    when(organizationRepository.save(expectedOrganizationDao.withId(null)))
        .thenReturn(Mono.just(expectedOrganizationDao));
    when(placeService.saveAll(any(), eq(organizationId))).thenReturn(Flux.empty());

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
    verify(placeService).saveAll(any(), eq(organizationId));
  }
}

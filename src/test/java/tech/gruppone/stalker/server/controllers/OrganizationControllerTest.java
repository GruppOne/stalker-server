package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.OrganizationService;
import tech.gruppone.stalker.server.services.PlaceService;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationControllerTest {

  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Autowired private WebTestClient webTestClient;

  @MockBean private OrganizationRepository organizationRepository;
  @MockBean private UserRepository userRepository;
  @MockBean private UserDataRepository userDataRepository;
  @MockBean private OrganizationService organizationService;
  @MockBean private PlaceService placeService;

  @Test
  void testGetOrganizationById() {

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

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organizationDao));
    when(placeService.findAllByOrganizationId(organizationId)).thenReturn(Flux.empty());

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
        .jsonPath("$.data.places")
        .isArray();

    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testPutOrganizationById() {

    final long organizationId = 1L;

    webTestClient
        .put()
        .uri("/organization/{organizationId}", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testDeleteOrganizationById() {
    final long organizationId = 1L;

    webTestClient
        .delete()
        .uri("/organization/{organizationId}", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testGetOrganizationByIdUsersInside() {
    final long organizationId = 1L;

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/inside", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

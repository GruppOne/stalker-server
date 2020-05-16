package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.services.PlaceService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private OrganizationRepository organizationRepository;
  @MockBean private PlaceService placeService;

  @Test
  public void testGetOrganizationById() {

    var organizationId = 1L;
    var name = "name";
    var description = "description";

    var organizationDao =
        OrganizationDao.builder()
            .id(organizationId)
            .name(name)
            .description(description)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
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
}

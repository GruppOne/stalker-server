package tech.gruppone.stalker.server.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlacesControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testGetOrganizationByIdPlaces() {
    final long organizationId = 1L;

    webTestClient
        .get()
        .uri("/organization/{organizationId}/places", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testPostOrganizationByIdPlaces() {
    final long organizationId = 1L;

    webTestClient
        .post()
        .uri("/organization/{organizationId}/places", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
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

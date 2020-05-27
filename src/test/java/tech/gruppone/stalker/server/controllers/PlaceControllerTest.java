package tech.gruppone.stalker.server.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaceControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testDeleteOrganizationByIdPlaceById() {

    final long organizationId = 1L;
    final long placeId = 1L;

    webTestClient
        .delete()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testGetOrganizationByIdPlaceById() {

    final long organizationId = 1L;
    final long placeId = 1L;

    webTestClient
        .get()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testPutOrganizationByIdPlaceById() {

    final long organizationId = 1L;
    final long placeId = 1L;

    webTestClient
        .put()
        .uri("/organization/{organizationId}/place/{placeId}", organizationId, placeId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

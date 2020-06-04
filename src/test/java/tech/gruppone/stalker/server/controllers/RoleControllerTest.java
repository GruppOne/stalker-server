package tech.gruppone.stalker.server.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testDeleteOrganizationByIdUserByIdRole() {
    final long userId = 1L;
    final long organizationId = 1L;

    webTestClient
        .delete()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testPostOrganizationByIdUserByIdRole() {
    final long userId = 1L;
    final long organizationId = 1L;

    webTestClient
        .post()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testPutOrganizationByIdUserByIdRole() {
    final long userId = 1L;
    final long organizationId = 1L;

    webTestClient
        .put()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

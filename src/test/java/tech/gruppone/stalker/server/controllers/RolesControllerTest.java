package tech.gruppone.stalker.server.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RolesControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testGetOrganizationByIdUsersRoles() {
    final long organizationId = 1L;

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/roles", organizationId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testGetUserByIdOrganizationsRoles() {
    final long userId = 1L;

    webTestClient
        .get()
        .uri("/user/{userId}/organizations/roles", userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }
}

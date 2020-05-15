package tech.gruppone.stalker.server.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VersionControllerTest {
  @Autowired private WebTestClient webTestClient;

  @Test
  public void testGetVersion() {
    webTestClient
        .get()
        .uri("/version")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.version")
        .isEqualTo("0.8.1");
  }
}

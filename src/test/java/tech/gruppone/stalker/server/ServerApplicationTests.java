package tech.gruppone.stalker.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @Test
  public void contextLoads() {
    // No endpoint is defined on this path.
    // if the server is not loading the error won't be 404 and the test will fail
    webTestClient.get().uri("/").exchange().expectStatus().isNotFound();
  }

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

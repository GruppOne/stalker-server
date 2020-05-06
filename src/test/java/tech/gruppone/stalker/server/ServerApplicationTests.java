package tech.gruppone.stalker.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  // TODO how to test that context loads correctly without requesting stuff from endpoints?
  @Test
  public void contextLoads() {
    webTestClient.get().uri("/version").exchange().expectStatus().isOk();
  }

}

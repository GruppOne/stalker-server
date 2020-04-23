package tech.gruppone.stalker.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

// XXX what does this do?
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void contextLoads() {
    webTestClient.get().uri("/").exchange().expectStatus().isNotFound();
  }

}

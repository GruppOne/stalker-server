package tech.gruppone.stalker.server;


import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.gruppone.stalker.server.model.Connection;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class testControllers {

  @Autowired
  WebTestClient testClient;

  @Test
  public void testUserConnectionToOrganization(){
    ;
    testClient.post()
      .uri("/user/1/organization/1/connection")
      .exchange()
      .expectStatus().isCreated();
    }

   @Test
   public void testUserDisconnectionToOrganization(){
    testClient.delete().uri("/user/1/organization/3/connection").exchange().expectStatus().isNoContent();
  }
}


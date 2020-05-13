package tech.gruppone.stalker.server;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import tech.gruppone.stalker.server.repositories.ConnectionRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionControllerTests {

  @Autowired
  WebTestClient testClient;

  @MockBean
  ConnectionRepository connectionRepository;

  @Test
  public void testCreateUserConnection(){
    ;
    testClient.post()
      .uri("/user/1/organization/1/connection")
      .exchange()
      .expectStatus().isCreated();
    }

   @Test
   public void testDeleteUserConnectionToOrganization(){
    testClient.delete()
      .uri("/user/1/organization/3/connection")
      .exchange()
      .expectStatus()
      .isNoContent();
  }

}

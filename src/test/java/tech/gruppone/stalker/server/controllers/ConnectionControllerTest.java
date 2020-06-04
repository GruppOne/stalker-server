package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.ConnectionController.PostUserByIdOrganizationByIdConnectionBody;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean ConnectionRepository connectionRepository;

  @Test
  void testPostUserByIdOrganizationByIdConnection() {

    final long userId = 1L;
    final long organizationId = 1L;

    final var connectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    when(connectionRepository.save(connectionDao)).thenReturn(Mono.empty());

    webTestClient
        .post()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .exchange()
        .expectStatus()
        .isCreated();

    // checks that the mock method has been called with the given parameters
    verify(connectionRepository).save(connectionDao);
  }

  @Test
  void testPostUserByIdOrganizationByIdConnectionWithRequestBody() {

    final long userId = 1L;
    final long organizationId = 1L;

    final String rdn = "rdn";
    final String ldapPassword = "ldapPassword";

    final var requestBody = new PostUserByIdOrganizationByIdConnectionBody(rdn, ldapPassword);

    webTestClient
        .post()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .body(Mono.just(requestBody), PostUserByIdOrganizationByIdConnectionBody.class)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_IMPLEMENTED);
  }

  @Test
  void testDeleteUserByIdOrganizationByIdConnection() {
    final long userId = 1L;
    final long organizationId = 1L;

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .exchange()
        .expectStatus()
        .isNoContent();

    // checks that the mock method has been called with the given parameters
    verify(connectionRepository).deleteByUserIdAndOrganizationId(userId, organizationId);
  }
}

package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.controllers.ConnectionController.PostUserByIdOrganizationByIdConnectionBody;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectionControllerTest {

  @Autowired WebTestClient webTestClient;

  @MockBean ConnectionRepository connectionRepository;
  @MockBean LdapConfigurationRepository ldapConfigurationRepository;
  @MockBean OrganizationRepository organizationRepository;

  final long userId = 1L;
  final long organizationId = 1L;

  @Test
  void testPostUserByIdOrganizationByIdConnection() {

    final var connectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    final var organization =
        OrganizationDao.builder()
            .id(organizationId)
            .name("name")
            .description(".description")
            .organizationType("public")
            .build();

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organization));
    when(connectionRepository.save(connectionDao)).thenReturn(Mono.empty());

    webTestClient
        .post()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .exchange()
        .expectStatus()
        .isCreated();

    // checks that the mock method has been called with the given parameters
    verify(connectionRepository).save(connectionDao);
    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testPostUserByIdOrganizationByIdConnectionPrivateOrganization() {

    final String ldapCn = "ldapCn";
    final String ldapPassword = "ldapPassword";

    final var requestBody = new PostUserByIdOrganizationByIdConnectionBody(ldapCn, ldapPassword);

    when(ldapConfigurationRepository.findByOrganizationId(organizationId)).thenReturn(Mono.empty());

    webTestClient
        .post()
        .uri("/user/{userId}/organization/{organizationId}/connection", userId, organizationId)
        .body(Mono.just(requestBody), PostUserByIdOrganizationByIdConnectionBody.class)
        .exchange()
        .expectStatus()
        .isBadRequest();

    verify(ldapConfigurationRepository).findByOrganizationId(organizationId);
  }

  @Test
  void testDeleteUserByIdOrganizationByIdConnection() {

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.just(1));

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

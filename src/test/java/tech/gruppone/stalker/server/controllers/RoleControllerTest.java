package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.controllers.RoleController.RoleRequestBody;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@Tag("integrationTest")
@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoleControllerTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Autowired WebTestClient webTestClient;

  @MockBean OrganizationRoleRepository organizationRoleRepository;

  @Test
  void testDeleteOrganizationByIdUserByIdRole() {
    final long userId = 1L;
    final long organizationId = 1L;

    when(organizationRoleRepository.deleteByOrganizationIdAndUserId(organizationId, userId))
        .thenReturn(Mono.just(1));

    webTestClient
        .delete()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NO_CONTENT)
        .expectBody()
        .isEmpty();

    verify(organizationRoleRepository).deleteByOrganizationIdAndUserId(organizationId, userId);
  }

  @Test
  void testPostOrganizationByIdUserByIdRole() {
    final long id = 1L;
    final long userId = 1L;
    final long organizationId = 1L;
    final AdministratorType newRole = AdministratorType.ADMIN;

    final var requestBody = new RoleRequestBody(newRole);

    final var organizationRole =
        OrganizationRoleDao.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(newRole)
            .build();

    when(organizationRoleRepository.save(organizationRole))
        .thenReturn(Mono.just(organizationRole.withId(id).withCreatedDate(LOCAL_DATETIME)));

    webTestClient
        .post()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .body(Mono.just(requestBody), RoleRequestBody.class)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.CREATED)
        .expectBody()
        .isEmpty();

    verify(organizationRoleRepository).save(organizationRole);
  }

  @Test
  void testPutOrganizationByIdUserByIdRole() {

    final long id = 1L;
    final var userId = 100L;
    final var organizationId = 100L;

    final AdministratorType oldRole = AdministratorType.VIEWER;
    final AdministratorType modifiedRole = AdministratorType.ADMIN;

    final var requestBody = new RoleRequestBody(modifiedRole);

    final var originalOrganizationRole =
        OrganizationRoleDao.builder()
            .id(id)
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(oldRole)
            .createdDate(LOCAL_DATETIME)
            .build();

    final var updatedOrganizationRole =
        originalOrganizationRole
            .withAdministratorType(modifiedRole)
            .withCreatedDate(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME);

    when(organizationRoleRepository.findByOrganizationIdAndUserId(organizationId, userId))
        .thenReturn(Mono.just(originalOrganizationRole));
    when(organizationRoleRepository.save(updatedOrganizationRole))
        .thenReturn(Mono.just(updatedOrganizationRole));

    webTestClient
        .put()
        .uri("/organization/{organizationId}/user/{userId}/role", organizationId, userId)
        .body(Mono.just(requestBody), RoleRequestBody.class)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NO_CONTENT)
        .expectBody()
        .isEmpty();

    verify(organizationRoleRepository).findByOrganizationIdAndUserId(organizationId, userId);
    verify(organizationRoleRepository).save(updatedOrganizationRole);
  }
}

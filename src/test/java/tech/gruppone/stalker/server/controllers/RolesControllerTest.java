package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RolesControllerTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Autowired WebTestClient webTestClient;

  @MockBean OrganizationRoleRepository organizationRoleRepository;

  @Test
  void testGetOrganizationByIdUsersRoles() {
    final long organizationId = 1L;

    final Long userId = 100L;
    final String role = "Admin";
    final AdministratorType administratorType = AdministratorType.ADMIN;

    final var organizationRoleDao =
        OrganizationRoleDao.builder()
            .organizationId(organizationId)
            .userId(userId)
            .administratorType(administratorType)
            .createdDate(LOCAL_DATETIME)
            .build();

    when(organizationRoleRepository.findAllByOrganizationId(organizationId))
        .thenReturn(Flux.just(organizationRoleDao));

    webTestClient
        .get()
        .uri("/organization/{organizationId}/users/roles", organizationId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.usersWithRoles")
        .isArray()
        .jsonPath("$.usersWithRoles[0].userId")
        .isEqualTo(userId)
        .jsonPath("$.usersWithRoles[0].role")
        .isEqualTo(role);

    verify(organizationRoleRepository).findAllByOrganizationId(organizationId);
  }

  @Test
  void testGetUserByIdOrganizationsRoles() {
    final long userId = 1L;

    final Long organizationId = 100L;
    final String role = "Viewer";
    final AdministratorType administratorType = AdministratorType.VIEWER;

    final var organizationRoleDao =
        OrganizationRoleDao.builder()
            .organizationId(organizationId)
            .userId(userId)
            .administratorType(administratorType)
            .createdDate(LOCAL_DATETIME)
            .build();

    when(organizationRoleRepository.findAllByUserId(userId))
        .thenReturn(Flux.just(organizationRoleDao));

    webTestClient
        .get()
        .uri("/user/{userId}/organizations/roles", userId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.rolesInOrganizations")
        .isArray()
        .jsonPath("$.rolesInOrganizations[0].organizationId")
        .isEqualTo(organizationId)
        .jsonPath("$.rolesInOrganizations[0].role")
        .isEqualTo(role);

    verify(organizationRoleRepository).findAllByUserId(userId);
  }
}

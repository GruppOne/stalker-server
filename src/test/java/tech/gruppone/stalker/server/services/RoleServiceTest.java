package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.api.RolesInOrganizationsDto;
import tech.gruppone.stalker.server.model.api.RolesInOrganizationsDto.RoleInOrganization;
import tech.gruppone.stalker.server.model.api.UsersWithRolesDto;
import tech.gruppone.stalker.server.model.api.UsersWithRolesDto.UserWithRole;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = RoleService.class)
class RoleServiceTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @MockBean OrganizationRoleRepository organizationRoleRepository;

  @Autowired RoleService roleService;

  @Test
  void testCreate() {
    final long id = 1L;
    final var userId = 100L;
    final var organizationId = 100L;

    final AdministratorType newRole = AdministratorType.ADMIN;
    final var organizationRole =
        OrganizationRoleDao.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(newRole)
            .build();

    when(organizationRoleRepository.save(organizationRole))
        .thenReturn(Mono.just(organizationRole.withId(id).withCreatedDate(LOCAL_DATETIME)));

    final var sut = roleService.create(userId, organizationId, newRole);

    StepVerifier.create(sut).verifyComplete();

    verify(organizationRoleRepository).save(organizationRole);
  }

  @Test
  void testCreateAlreadyExisting() {
    final var userId = 100L;
    final var organizationId = 100L;

    final AdministratorType newRole = AdministratorType.ADMIN;
    final var organizationRole =
        OrganizationRoleDao.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(newRole)
            .build();

    when(organizationRoleRepository.save(organizationRole))
        .thenReturn(Mono.error(new DataIntegrityViolationException("message")));

    final var sut = roleService.create(userId, organizationId, newRole);

    StepVerifier.create(sut).expectError(BadRequestException.class).verify();

    verify(organizationRoleRepository).save(organizationRole);
  }

  @Test
  void testUpdate() {

    final long id = 1L;
    final var userId = 100L;
    final var organizationId = 100L;

    final AdministratorType oldRole = AdministratorType.VIEWER;
    final AdministratorType modifiedRole = AdministratorType.ADMIN;

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

    final var sut = roleService.update(userId, organizationId, modifiedRole);

    StepVerifier.create(sut).verifyComplete();

    verify(organizationRoleRepository).findByOrganizationIdAndUserId(organizationId, userId);
    verify(organizationRoleRepository).save(updatedOrganizationRole);
  }

  @Test
  void testFindUsersWithRolesByOrganizationId() {
    final long organizationId = 1L;

    final Long userId = 100L;
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

    final UsersWithRolesDto expectedUsersWithRolesDto =
        new UsersWithRolesDto(List.of(new UserWithRole(userId, administratorType)));

    final var sut = roleService.findUsersWithRolesByOrganizationId(organizationId);

    StepVerifier.create(sut).expectNext(expectedUsersWithRolesDto).verifyComplete();

    verify(organizationRoleRepository).findAllByOrganizationId(organizationId);
  }

  @Test
  void testFindRolesInOrganizationsByUserId() {
    final long userId = 1L;

    final Long organizationId = 100L;
    final AdministratorType administratorType = AdministratorType.VIEWER;

    final var organizationRoleDao =
        OrganizationRoleDao.builder()
            .organizationId(organizationId)
            .userId(userId)
            .administratorType(administratorType)
            .createdDate(LOCAL_DATETIME)
            .build();

    final RolesInOrganizationsDto expectedRolesInOrganizationsDto =
        new RolesInOrganizationsDto(
            List.of(new RoleInOrganization(organizationId, administratorType)));

    when(organizationRoleRepository.findAllByUserId(userId))
        .thenReturn(Flux.just(organizationRoleDao));

    final var sut = roleService.findRolesInOrganizationsByUserId(userId);

    StepVerifier.create(sut).expectNext(expectedRolesInOrganizationsDto).verifyComplete();

    verify(organizationRoleRepository).findAllByUserId(userId);
  }
}

package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.OrganizationRoleDto;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationServiceTest {

  @MockBean OrganizationRoleRepository organizationRoleRepository;
  @Autowired OrganizationRoleService organizationRoleService;

  @Test
  public void testFindUsersRoles() {

    // var organizationId = 1L;
    // var userId = 2L;
    // var nameRole = "Admin";
    // var createdDateRole = LocalDateTime.now();
    // var nameOrganization = "GruppOne";
    // var descriptionOrganization = "Descrizione GruppOne";
    // var isPrivate = 0L;
    // var createdDateOrganization = LocalDateTime.parse("2020-05-22 22:50:54");
    // var lastModifiedDateOrganization = LocalDateTime.parse("2020-05-22 22:50:54");
    // var emailUser = "gruppone.swe@gmail.com";
    // var passwordUser = "password";
    // var roleId = 1L;
    // var role = "ROLE_ADMIN";

    // OrganizationRoleDao organizationRoleDao =
    // OrganizationRoleDao.builder().organizationId(organizationId).userId(userId).administratorType(roleId).createdDate(createdDateRole).build();
    // OrganizationDao organization =
    // OrganizationDao.builder().id(organizationId).name(nameOrganization).description(descriptionOrganization).isPrivate(isPrivate).createdDate(createdDateOrganization).lastModifiedDate(lastModifiedDateOrganization).build();
    // UserDao user = UserDao.builder().id(userId).email(emailUser).password(passwordUser).build();
    // AdministratorTypeDao administratorType =
    // AdministratorTypeDao.builder().id(roleId).name(nameRole).role(role).build();

    // when(organizationRoleRepository.findUsersRoles(organizationId)).thenReturn(Flux.empty());

    var organizationId = 1L;
    var userId = 2L;
    var administratorType = 1L;
    var role = "Admin";

    var organizationRoleDao =
        OrganizationRoleDao.builder()
            .organizationId(organizationId)
            .userId(userId)
            .administratorType(administratorType)
            .name(role)
            .createdDate(LocalDateTime.now())
            .build();

    when(organizationRoleRepository.findUsersRoles(organizationId))
        .thenReturn(Flux.just(organizationRoleDao));

    Flux<OrganizationRoleDto> resultToCheck =
        Flux.just(OrganizationRoleDto.builder().userId(userId).role(role).build());

    Flux<OrganizationRoleDto> sut = organizationRoleService.getUsersRoles(organizationId);

    Assertions.assertThat(resultToCheck.blockFirst()).isEqualTo(sut.blockFirst());
  }
}

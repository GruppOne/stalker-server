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
class OrganizationServiceTest {

  @MockBean OrganizationRoleRepository organizationRoleRepository;
  @Autowired OrganizationRoleService organizationRoleService;

  @Test
  void testFindUsersRoles() {

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

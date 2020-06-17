package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = ConnectionService.class)
class ConnectionServiceTest {

  @MockBean ConnectionRepository connectionRepository;
  @MockBean OrganizationRepository organizationRepository;
  @MockBean LdapConfigurationRepository ldapConfigurationRepository;

  @Autowired ConnectionService connectionService;

  final long userId = 1L;
  final long organizationId = 11L;

  final OrganizationDao organization =
      OrganizationDao.builder()
          .id(organizationId)
          .name("name")
          .description(".description")
          .organizationType("public")
          .build();

  @Test
  void testForceCreateConnection() {

    final var expectedConnectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    when(connectionRepository.save(expectedConnectionDao))
        .thenReturn(Mono.just(expectedConnectionDao));

    final var sut = connectionService.forceCreateConnection(userId, organizationId);

    StepVerifier.create(sut).verifyComplete();

    verify(connectionRepository).save(expectedConnectionDao);
  }

  @Test
  void testCreatePublicConnection() {

    final var expectedConnectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    when(connectionRepository.save(expectedConnectionDao))
        .thenReturn(Mono.just(expectedConnectionDao));

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organization));

    final var sut = connectionService.createPublicConnection(userId, organizationId);

    StepVerifier.create(sut).verifyComplete();

    verify(connectionRepository).save(expectedConnectionDao);
    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testCreatePublicConnectionWhenExistingConnection() {

    final var expectedConnectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    when(connectionRepository.save(expectedConnectionDao))
        .thenReturn(Mono.error(new DataIntegrityViolationException("message")));

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organization));

    final var sut = connectionService.createPublicConnection(userId, organizationId);

    StepVerifier.create(sut).verifyError(BadRequestException.class);

    verify(connectionRepository).save(expectedConnectionDao);
    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testCreatePublicConnectionWhenOrganizationIsPrivate() {
    final OrganizationDao privateOrganization =
        OrganizationDao.builder()
            .id(organizationId)
            .name("name")
            .description(".description")
            .organizationType("private")
            .build();

    when(organizationRepository.findById(organizationId))
        .thenReturn(Mono.just(privateOrganization));

    final var sut = connectionService.createPublicConnection(userId, organizationId);

    StepVerifier.create(sut).verifyError(BadRequestException.class);

    verify(organizationRepository).findById(organizationId);
  }

  @Test
  void testCreatePrivateConnectionWhenNoLdapConfiguration() {

    final var ldapUserCn = "ldapUserCn";
    final var ldapUserPassword = "ldapUserPassword";

    when(ldapConfigurationRepository.findByOrganizationId(organizationId)).thenReturn(Mono.empty());

    final var sut =
        connectionService.createPrivateConnection(
            ldapUserCn, ldapUserPassword, userId, organizationId);

    StepVerifier.create(sut).verifyError(BadRequestException.class);

    verify(ldapConfigurationRepository).findByOrganizationId(organizationId);
  }

  @Test
  void testDeleteConnection() {

    final long userId = 1L;
    final long organizationId = 11L;

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.just(1));

    final var sut = connectionService.deleteConnection(userId, organizationId);

    StepVerifier.create(sut).verifyComplete();

    verify(connectionRepository).deleteByUserIdAndOrganizationId(userId, organizationId);
  }

  @Test
  void testDeleteUserConnectionWhenNoConnection() {

    final long userId = 1L;
    final long organizationId = 11L;

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.just(0));

    final var sut = connectionService.deleteConnection(userId, organizationId);

    StepVerifier.create(sut).verifyError(NotFoundException.class);

    verify(connectionRepository).deleteByUserIdAndOrganizationId(userId, organizationId);
  }
}

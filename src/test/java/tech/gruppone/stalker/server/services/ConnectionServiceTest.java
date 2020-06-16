package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = ConnectionService.class)
class ConnectionServiceTest {

  @MockBean ConnectionRepository connectionRepository;

  @Autowired ConnectionService connectionService;

  @Test
  void testDeleteUserConnection() {

    final long userId = 1L;
    final long organizationId = 11L;

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.just(1));

    final var sut = connectionService.deleteUserConnection(userId, organizationId);

    StepVerifier.create(sut).verifyComplete();

    verify(connectionRepository).deleteByUserIdAndOrganizationId(userId, organizationId);
  }

  @Test
  void testDeleteUserConnectionWhenNoConnection() {

    final long userId = 1L;
    final long organizationId = 11L;

    when(connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId))
        .thenReturn(Mono.just(0));

    final var sut = connectionService.deleteUserConnection(userId, organizationId);

    StepVerifier.create(sut).verifyError(NotFoundException.class);

    verify(connectionRepository).deleteByUserIdAndOrganizationId(userId, organizationId);
  }
}

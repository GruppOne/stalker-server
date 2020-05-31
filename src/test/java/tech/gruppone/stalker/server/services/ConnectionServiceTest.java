package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");
  private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @Mock ConnectionRepository connectionRepository;
  @Mock UserRepository userRepository;
  @Mock UserDataRepository userDataRepository;

  @InjectMocks ConnectionService connectionService;

  @Test
  void testFindConnectedUsersByOrganizationId() {

    // ARRANGE
    final long organizationId = 1L;

    final String password = "password";
    final String lastName = "lastName";
    final LocalDate birthdate = LOCAL_DATE;
    final LocalDateTime creationDateTime = LOCAL_DATETIME;
    final LocalDateTime lastModifiedDate = LOCAL_DATETIME;

    final long userId1 = 1L;
    final String email1 = "email1";
    final String firstName1 = "firstName1";

    final long userId2 = 2L;
    final String email2 = "email2";
    final String firstName2 = "firstName2";

    final UserDao userDao1 = UserDao.builder().id(userId1).email(email1).password(password).build();
    final UserDataDao userData1 =
        UserDataDao.builder()
            .userId(userId1)
            .firstName(firstName1)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    final UserDao userDao2 = UserDao.builder().id(userId2).email(email2).password(password).build();
    final UserDataDao userData2 =
        UserDataDao.builder()
            .userId(userId2)
            .firstName(firstName2)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    var connectedUserIds = Flux.just(userId1, userId2);
    when(connectionRepository.findConnectedUserIdsByOrganizationId(organizationId))
        .thenReturn(connectedUserIds);

    when(userRepository.findAllById(connectedUserIds)).thenReturn(Flux.just(userDao1, userDao2));
    when(userDataRepository.findAllById(connectedUserIds))
        .thenReturn(Flux.just(userData2, userData1));

    var expectedUserDto1 =
        new UserDto(
            userId1,
            UserDataDto.builder()
                .email(email1)
                .firstName(firstName1)
                .lastName(lastName)
                .birthDate(birthdate)
                .creationDateTime(Timestamp.valueOf(creationDateTime))
                .build());
    var expectedUserDto2 =
        new UserDto(
            userId2,
            UserDataDto.builder()
                .email(email2)
                .firstName(firstName2)
                .lastName(lastName)
                .birthDate(birthdate)
                .creationDateTime(Timestamp.valueOf(creationDateTime))
                .build());
    // ACT
    var sut = connectionService.findConnectedUsersByOrganizationId(organizationId);

    StepVerifier.create(sut)
        .expectNext(expectedUserDto1)
        .expectNext(expectedUserDto2)
        .verifyComplete();
  }
}

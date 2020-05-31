package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

  @TestConfiguration
  static class TestConfig {
    public static final LocalDateTime FIXED_LOCAL_DATE_TIME =
        LocalDateTime.parse("2020-01-01T01:01:01.01");

    // override system clock with a fixed clock
    @Bean
    @Primary
    public Clock fixedClock() {
      return Clock.fixed(
          FIXED_LOCAL_DATE_TIME.toInstant(ZoneOffset.ofHours(1)), ZoneId.systemDefault());
    }
  }

  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");
  private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  @Autowired UserService userService;

  @Test
  void testFindById() {
    // ARRANGE
    final long userId = 1L;
    final String email = "email";
    final String firstName = "firstName";
    final String lastName = "lastName";
    final LocalDate birthdate = LOCAL_DATE;
    final LocalDateTime creationDateTime = LOCAL_DATETIME;
    final LocalDateTime lastModifiedDate = LOCAL_DATETIME;

    final UserDao userDao = UserDao.builder().id(userId).email(email).password("password").build();
    final UserDataDao userData =
        UserDataDao.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    final var userDataDto =
        UserDataDto.builder()
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .creationDateTime(Timestamp.valueOf(creationDateTime))
            .build();

    final UserDto expectedUser = new UserDto(userId, userDataDto);

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao));
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(userData));

    // ACT
    final Mono<UserDto> sut = userService.findById(userId);

    // ASSERT
    sut.as(StepVerifier::create).expectNext(expectedUser).verifyComplete();

    verify(userRepository).findById(userId);
    verify(userDataRepository).findById(userId);
  }

  @Test
  void testUpdatePassword() {
    // ARRANGE
    final long userId = 1L;
    final String email = "email@email.email";
    final String oldPassword = "oldPassword";
    final String newPassword = "newPassword";

    final UserDao oldUserDao =
        UserDao.builder().id(userId).email(email).password(oldPassword).build();
    final UserDao newUserDao =
        UserDao.builder().id(userId).email(email).password(newPassword).build();
    ;

    when(userRepository.findById(userId)).thenReturn(Mono.just(oldUserDao));
    when(userRepository.save(newUserDao)).thenReturn(Mono.just(newUserDao));

    // ACT
    final Mono<Void> sut = userService.updatePassword(oldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).verifyComplete();
  }

  @Test
  void testUpdateUserById() {

    final long userId = 1L;
    final String email = "email@email.email";
    final String password = "password";
    final String newFirstName = "newFirstName";
    final String lastName = "lastName";
    final LocalDate birthdate = LOCAL_DATE;
    final LocalDateTime localDateTime = LOCAL_DATETIME;

    final UserDataDto userDataDto =
        UserDataDto.builder()
            .email(email)
            .firstName(newFirstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .creationDateTime(Timestamp.valueOf(localDateTime))
            .build();

    final UserDao userDao = UserDao.builder().id(userId).email(email).password(password).build();

    final var expectedUserDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(newFirstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(localDateTime)
            .lastModifiedDate(TestConfig.FIXED_LOCAL_DATE_TIME)
            .build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao));
    when(userDataRepository.save(expectedUserDataDao)).thenReturn(Mono.just(expectedUserDataDao));

    // ACT
    final Mono<Void> sut = userService.updateUserById(userDataDto, userId);

    // ASSERT
    sut.as(StepVerifier::create).verifyComplete();

    verify(userRepository).findById(userId);
    verify(userDataRepository).save(expectedUserDataDao);
  }
}

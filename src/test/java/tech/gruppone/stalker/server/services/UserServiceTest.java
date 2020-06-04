package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");
  private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  @Autowired UserService userService;

  @Test
  void testFindAll() {

    final long userId = 1L;
    final String email = "email";
    final String password = "password";

    final String firstName = "firstName";
    final String lastName = "lastName";
    final LocalDate birthdate = LOCAL_DATE;
    final LocalDateTime creationDateTime = LOCAL_DATETIME;
    final LocalDateTime lastModifiedDate = LOCAL_DATETIME;

    final UserDao userDao = UserDao.builder().id(userId).email(email).password(password).build();
    final UserDataDao userData =
        UserDataDao.builder()
            .userId(userId)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    when(userRepository.findAll()).thenReturn(Flux.just(userDao));
    when(userDataRepository.findAll()).thenReturn(Flux.just(userData));

    final var expectedUserDto =
        new UserDto(
            userId,
            UserDataDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthdate)
                .creationDateTime(Timestamp.valueOf(creationDateTime))
                .build());

    final var sut = userService.findAll();

    StepVerifier.create(sut).expectNext(expectedUserDto).verifyComplete();
  }

  @Test
  void testFindAllById() {

    // ARRANGE
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

    final var userIds = Flux.just(userId1, userId2);

    when(userRepository.findAllById(userIds)).thenReturn(Flux.just(userDao1, userDao2));
    when(userDataRepository.findAllById(userIds)).thenReturn(Flux.just(userData2, userData1));

    final var expectedUserDto1 =
        new UserDto(
            userId1,
            UserDataDto.builder()
                .email(email1)
                .firstName(firstName1)
                .lastName(lastName)
                .birthDate(birthdate)
                .creationDateTime(Timestamp.valueOf(creationDateTime))
                .build());
    final var expectedUserDto2 =
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
    final var sut = userService.findAllById(userIds);

    // ASSERT
    StepVerifier.create(sut)
        .expectNext(expectedUserDto1)
        .expectNext(expectedUserDto2)
        .verifyComplete();

    verify(userRepository).findAllById(userIds);
    verify(userDataRepository).findAllById(userIds);
  }

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
    final UserDataDao userDataDao =
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
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(userDataDao));

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

    verify(userRepository).findById(userId);
    verify(userRepository).save(newUserDao);
  }

  @Test
  void testUpdatePasswordWrongOldPassword() {
    // ARRANGE
    final long userId = 1L;
    final String email = "email@email.email";
    final String wrongOldPassword = "wrongOldPassword";
    final String savedOldPassword = "savedOldPassword";
    final String newPassword = "newPassword";

    final UserDao oldUserDao =
        UserDao.builder().id(userId).email(email).password(savedOldPassword).build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(oldUserDao));

    // ACT
    final Mono<Void> sut = userService.updatePassword(wrongOldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).expectError(NotFoundException.class).verify();

    verify(userRepository).findById(userId);
  }

  @Test
  void testUpdatePasswordBlankNewPassword() {
    // ARRANGE
    final long userId = 1L;
    final String oldPassword = "oldPassword";
    final String newPassword = "";

    // ACT
    final Mono<Void> sut = userService.updatePassword(oldPassword, newPassword, userId);

    // ASSERT
    sut.as(StepVerifier::create).expectError(BadRequestException.class).verify();
  }

  @Test
  void testUpdateUserById() {

    final long userId = 1L;
    final String email = "email@email.email";
    final String password = "password";
    final LocalDate birthDate = LOCAL_DATE;
    final LocalDateTime creationDateTime = LOCAL_DATETIME;

    final String oldFirstName = "oldFirstName";
    final String oldLastName = "oldLastName";

    final String newFirstName = "newFirstName";
    final String newLastName = "newLastName";

    final UserDataDto userDataDto =
        UserDataDto.builder()
            .email(email)
            .firstName(newFirstName)
            .lastName(newLastName)
            .birthDate(birthDate)
            .creationDateTime(Timestamp.valueOf(creationDateTime))
            .build();

    final UserDao userDao = UserDao.builder().id(userId).email(email).password(password).build();

    final var originalUserDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(oldFirstName)
            .lastName(oldLastName)
            .birthDate(birthDate)
            .createdDate(creationDateTime)
            .lastModifiedDate(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME)
            .build();

    // modify firstName and lastName
    final var expectedUserDataDao =
        UserDataDao.builder()
            .userId(userId)
            .firstName(newFirstName)
            .lastName(newLastName)
            .birthDate(birthDate)
            .createdDate(creationDateTime)
            .lastModifiedDate(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME)
            .build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao));
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(originalUserDataDao));
    when(userDataRepository.save(expectedUserDataDao)).thenReturn(Mono.just(expectedUserDataDao));

    // ACT
    final Mono<Void> sut = userService.updateUserById(userDataDto, userId);

    // ASSERT
    sut.as(StepVerifier::create).verifyComplete();

    verify(userRepository).findById(userId);
    verify(userDataRepository).findById(userId);
    verify(userDataRepository).save(expectedUserDataDao);
  }
}

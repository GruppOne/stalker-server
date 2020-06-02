package tech.gruppone.stalker.server.services;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignUpServiceTest {
  private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 1);

  @Autowired SignUpService signUpService;

  @MockBean LoginService loginService;
  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  // correct fields
  private static final String correctEmail = "email@email.email";
  private static final String correctPassword =
      "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531";
  private static final String correctFirstName = "firstName";
  private static final String correctLastName = "lastName";

  @Test
  void testCreateNewUser() {

    final var loginDataDto = new LoginDataDto(correctEmail, correctPassword);
    final var userDataDto =
        UserDataDto.builder()
            .email(correctEmail)
            .firstName(correctFirstName)
            .lastName(correctLastName)
            .birthDate(LOCAL_DATE)
            .build();

    long createdUserId = 11111L;

    final var userDao = UserDao.builder().email(correctEmail).password(correctPassword).build();
    final var userDataDao =
        UserDataDao.builder()
            .userId(createdUserId)
            .firstName(correctFirstName)
            .lastName(correctLastName)
            .birthDate(LOCAL_DATE)
            // .createdDate(LOCAL_DATETIME)
            // .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(userRepository.save(userDao)).thenReturn(Mono.just(userDao.withId(createdUserId)));
    when(userDataRepository.save(userDataDao)).thenReturn(Mono.just(userDataDao));

    final var jwtToken = "jwt-token";
    when(loginService.login(correctEmail, correctPassword)).thenReturn(Mono.just(jwtToken));

    // ACT
    Mono<String> sut = signUpService.createNewUser(loginDataDto, userDataDto);

    // ASSERT
    StepVerifier.create(sut).expectNext(jwtToken).verifyComplete();

    verify(userRepository).save(userDao);
    verify(userDataRepository).save(userDataDao);
    verify(loginService).login(correctEmail, correctPassword);
  }

  static Stream<Arguments> testParameters() {
    // loginDataEmail, userDataEmail, password, firstName, lastName
    return Stream.of(
        arguments("wrong email", correctEmail, correctPassword, correctFirstName, correctLastName),
        arguments(correctEmail, "wrong email", correctPassword, correctFirstName, correctLastName),
        arguments("", "", correctPassword, correctFirstName, correctLastName),
        arguments(correctEmail, correctEmail, "short password", correctFirstName, correctLastName),
        arguments(correctEmail, correctEmail, correctPassword, "", correctLastName),
        arguments(correctEmail, correctEmail, correctPassword, correctFirstName, ""));
  }

  @ParameterizedTest
  @MethodSource("testParameters")
  void testCreateNewUserFailsWithInvalidParameters(
      String loginDataEmail,
      String userDataEmail,
      String password,
      String firstName,
      String lastName) {
    final var loginDataDto = new LoginDataDto(loginDataEmail, password);

    final var userDataDto =
        UserDataDto.builder()
            .email(userDataEmail)
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(LOCAL_DATE)
            .build();

    final var sut = signUpService.createNewUser(loginDataDto, userDataDto);

    sut.as(StepVerifier::create).expectError(BadRequestException.class).verify();
  }
}

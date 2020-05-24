package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest
public class UsersServiceTest {

  @Autowired JwtService jwtService;
  @Autowired UsersService usersService;
  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  @Test
  public void testFindAll() {
    // ARRANGE
    long userId = 1L;
    String email = "ciaociao@hotmail.it";
    String firstName = "Riccardo";
    String lastName = "Cestaro";
    LocalDate birthdate = LocalDate.parse("1960-04-16");
    LocalDateTime creationDateTime = LocalDateTime.parse("2020-05-14T14:34:50");
    LocalDateTime lastModifiedDate = LocalDateTime.parse("2020-05-14T14:34:50");

    UserDao user = UserDao.builder().id(userId).email(email).password("password").build();
    UserDataDao userData =
        UserDataDao.builder()
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    when(userRepository.findAll()).thenReturn(Flux.just(user));

    when(userDataRepository.findAll()).thenReturn(Flux.just(userData));
    // ACT
    Flux<UserDto> userToCheck =
        Flux.just(
            UserDto.builder()
                .id(userId)
                .data(
                    UserDataDto.builder()
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthDate(birthdate)
                        .creationDateTime(Timestamp.valueOf(creationDateTime))
                        .build())
                .build());

    Flux<UserDto> sut = usersService.findAll();
    // ASSERTION

    Assertions.assertThat(userToCheck.collectList().block()).isEqualTo(sut.collectList().block());
  }

  @Test
  public void testSignUpUser() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmail.it")
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao =
        UserDataDao.builder()
            .userId(11L)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    final var userDao =
        UserDao.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();

    doReturn(Mono.just(userDao)).when(userRepository).save(userDao);
    doReturn(Mono.just(userDataDao))
        .when(userDataRepository)
        .insert(
            userDataDao.getUserId(),
            userDataDao.getFirstName(),
            userDataDao.getLastName(),
            userDataDao.getBirthDate());
    doReturn(Mono.just(userDao)).when(userRepository).findByEmail(loginDataDto.getEmail());
    String jwtToken = jwtService.createToken(11L);
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectNext(jwtToken);
  }

  /*@Test
  public void testSignUpUserWithEmailJustUsed(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmail.it").firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao = UserDataDao.builder().userId(11L).firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();
    final var userDao = UserDao.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();

    doReturn(Mono.error(new BadRequestException())).when(userRepository).save(userDao);

    String jwtToken = jwtService.createToken(11L);
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);

  }*/

  @Test
  public void testSignUpUserWithMissingEmailOnLoginData() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmail.it")
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao =
        UserDataDao.builder()
            .userId(10L)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    final var userDao =
        UserDao.builder()
            .id(10L)
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();

    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingEmailOnUserData() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("")
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao =
        UserDataDao.builder()
            .userId(10L)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    final var userDao =
        UserDao.builder()
            .id(10L)
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();

    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingfirstName() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmail.it")
            .firstName("")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao =
        UserDataDao.builder()
            .userId(10L)
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
    final var userDao =
        UserDao.builder()
            .id(10L)
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();

    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingLastName() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmai.it")
            .firstName("Mario")
            .lastName("")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }
  /*@Test
  public void testSignUpUserWithMissingBirthDate(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmai.it").firstName("Mario").lastName("Rossi").birthDate(new LocalDate(0
     0 0)).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }*/

  @Test
  public void testSignUpUserWithShortPassword() {

    final var loginDataDto =
        LoginDataDto.builder()
            .email("mariorossi@hotmail.it")
            .password(
                "ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956e")
            .build();
    final var userDataDto =
        UserDataDto.builder()
            .email("mariorossi@hotmai.it")
            .firstName("Mario")
            .lastName("Rossi")
            .birthDate(LocalDate.now())
            .build();
    final var userWithLoginDataDto =
        UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }
}

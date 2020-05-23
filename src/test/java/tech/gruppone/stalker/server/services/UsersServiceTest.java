package tech.gruppone.stalker.server.services;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest
public class UsersServiceTest {

  @Autowired JwtService jwtService;
  @Autowired UsersService usersService;
  @MockBean UserRepository userRepository;
  @MockBean UserDataRepository userDataRepository;

  @Test
  public void testSignUpUserWithMissingEmailOnLoginData(){

    final var loginDataDto = LoginDataDto.builder().email("").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmail.it").firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingEmailOnUserData(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("").firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingfirstName(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmail.it").firstName("").lastName("Rossi").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }

  @Test
  public void testSignUpUserWithMissingLastName(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmai.it").firstName("Mario").lastName("").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }
  /*@Test
  public void testSignUpUserWithMissingBirthDate(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmai.it").firstName("Mario").lastName("Rossi").birthDate(new LocalDate(0 0 0)).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }*/

  @Test
  public void testSignUpUserWithShortPassword(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956e").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmai.it").firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();
    Mono<String> sut = usersService.signUpUser(userWithLoginDataDto);

    StepVerifier.create(sut).expectError(BadRequestException.class);
  }


}

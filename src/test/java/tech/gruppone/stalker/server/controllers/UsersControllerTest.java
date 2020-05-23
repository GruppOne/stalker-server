package tech.gruppone.stalker.server.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.JwtService;
import tech.gruppone.stalker.server.services.UsersService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerTest {

  @Autowired
  WebTestClient testClient;

  @Autowired
  JwtService jwtService;

  @MockBean
  UsersService usersService;

  @MockBean
  UserRepository userRepository;

  @MockBean
  UserDataRepository userDataRepository;

  @Test
  public void testCreateUser(){

    final var loginDataDto = LoginDataDto.builder().email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();
    final var userDataDto = UserDataDto.builder().email("mariorossi@hotmail.it").firstName("Mario").lastName("Rossi").birthDate(LocalDate.now()).build();
    final var userWithLoginDataDto = UserDataWithLoginData.builder().loginData(loginDataDto).userData(userDataDto).build();

    final var userDataDao = UserDataDao.builder().userId(10L).firstName("Mario").lastName("Rossi").birthDate(
      LocalDate.now()).createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();
    final var userDao = UserDao.builder().id(10L).email("mariorossi@hotmail.it").password("ba191a9ej8625cacdf7dfe60e97728b88dfac7e1b6b90b853dbc6677cfdacf241630ba38d3d7446d7d781417aa1956ecd68d651a8da4523b134144e6ccb0a531").build();


    doReturn(Mono.just(userDao)).when(userRepository).save(userDao);
    doReturn(Mono.just(userDataDao)).when(userDataRepository).insert(userDataDao.getUserId(), userDataDao.getFirstName(), userDataDao.getLastName(), userDataDao.getBirthDate());
    doReturn(Mono.just(userDao)).when(userRepository).findByEmail(loginDataDto.getEmail());
    doReturn(Mono.just(jwtService.createToken(10L))).when(usersService).signUpUser(userWithLoginDataDto);
    //when(userDataRepository.insert(userDataDao.getUserId(), userDataDao.getFirstName(), userDataDao.getLastName(), userDataDao.getBirthDate())).thenReturn(Mono.just(userDataDao));
    //when(userRepository.findByEmail(loginDataDto.getEmail())).thenReturn(Mono.just(userDao));
    //when(usersService.signUpUser(userWithLoginDataDto)).thenReturn(jwtService.createToken(10L));



    testClient
      .post()
      .uri("/users")
      .body(Mono.just(userWithLoginDataDto), UserDataWithLoginData.class)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody()
      .jsonPath("$.jwt").exists();


  }

}

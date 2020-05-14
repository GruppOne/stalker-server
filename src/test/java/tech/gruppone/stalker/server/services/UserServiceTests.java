package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {
  @Autowired
  WebTestClient testClient;
  @MockBean
  UserDataRepository userDataRepository;
  @MockBean
  UserRepository userRepository;
  @Autowired
  UserService userService;


  @Test
  public void testFindById() {
    // ARRANGE
    long userId = 1L;
    String email = "ciaociao@hotmail.it";
    String firstName = "Riccardo";
    String lastName = "Cestaro";
    LocalDate birthdate = LocalDate.parse("1960-04-16");
    LocalDateTime creationDateTime = LocalDateTime.parse("2020-05-14T14:34:50");
    LocalDateTime lastModifiedDate = LocalDateTime.parse("2020-05-14T14:34:50");

    UserDao user = UserDao.builder().id(userId).email(email).password("password").build();
    UserDataDao userData = UserDataDao.builder().firstName(firstName).lastName(lastName).birthDate(birthdate).createdDate(creationDateTime).lastModifiedDate(lastModifiedDate).build();

    when(userRepository.findById(userId)).thenReturn(
     Mono.just(user));

     when(userDataRepository.findById(userId)).thenReturn(
      Mono.just(userData));
    // ACT
    Mono<UserDto> userToCheck = Mono.just(UserDto.builder().id(userId).userData(UserDataDto.builder().email(email).firstName(firstName).lastName(lastName).birthDate(birthdate).creationDateTime(creationDateTime).build()).build());

    Mono<UserDto> sut = userService.findById(userId);
    // ASSERTION

    Assertions.assertThat(userToCheck.block()).isEqualTo(sut.block());
  }

}

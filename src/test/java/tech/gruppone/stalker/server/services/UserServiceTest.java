package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {
  @MockBean UserDataRepository userDataRepository;
  @MockBean UserRepository userRepository;
  @Autowired UserService userService;

  @Test
  public void testFindById() {
    // ARRANGE
    final long userId = 1L;
    final String email = "ciaociao@hotmail.it";
    final String firstName = "Riccardo";
    final String lastName = "Cestaro";
    final LocalDate birthdate = LocalDate.parse("1960-04-16");
    final LocalDateTime creationDateTime = LocalDateTime.parse("2020-05-14T14:34:50");
    final LocalDateTime lastModifiedDate = LocalDateTime.parse("2020-05-14T14:34:50");

    final UserDao user = UserDao.builder().id(userId).email(email).password("password").build();
    final UserDataDao userData =
        UserDataDao.builder()
            .firstName(firstName)
            .lastName(lastName)
            .birthDate(birthdate)
            .createdDate(creationDateTime)
            .lastModifiedDate(lastModifiedDate)
            .build();

    when(userRepository.findById(userId)).thenReturn(Mono.just(user));
    when(userDataRepository.findById(userId)).thenReturn(Mono.just(userData));

    // ACT
    final Mono<UserDto> userToCheck =
        Mono.just(
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

    final Mono<UserDto> sut = userService.findById(userId);

    // ASSERT
    assertThat(userToCheck.block()).isEqualTo(sut.block());
  }

  @Test
  public void testUpdateUser() {
    final long userId = 1L;
    final String oldPassword = "mela";
    final String newPassword = "ciao";

    final UserDao userDao1 =
        UserDao.builder().id(userId).email("marioRossi@gmail.com").password(oldPassword).build();
    final UserDao userDao2 =
        UserDao.builder().id(userId).email("marioRossi@gmail.com").password(newPassword).build();

    // doReturn(Mono.just(userDao)).when(userRepository).save(userDao);

    when(userRepository.findById(userId)).thenReturn(Mono.just(userDao1));
    when(userRepository.save(userDao2)).thenReturn(Mono.just(userDao2));

    final Mono<Void> response = userService.updatePassword(oldPassword, newPassword, userId);

    assertThat(response.block()).isEqualTo(null);
  }
}

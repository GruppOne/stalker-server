package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginServiceTest {

  @MockBean private UserRepository userRepository;
  @MockBean private JwtService jwtService;

  @Autowired private LoginService loginService;

  @Test
  public void testLogUser() {
    // Arrange
    Long id = 1L;
    String email = "mario@gmail.com";
    String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    LoginDataDto loginData = new LoginDataDto(email, password);

    UserDao user = UserDao.builder().email(email).password(password).id(id).build();
    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

    String expectedEncodedJwt = jwtService.createToken(id);

    // Act
    Mono<String> sut = loginService.logUser(loginData);

    // Assert

    StepVerifier.create(sut).expectNext(expectedEncodedJwt);
  }

  @Test
  public void testLogUserWithWrongPassword() {
    // Arrange
    Long id = 1L;
    String email = "mario@gmail.com";
    String savedPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2351";
    String wrongPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    LoginDataDto loginData = new LoginDataDto(email, wrongPassword);

    UserDao savedUser = UserDao.builder().email(email).password(savedPassword).id(id).build();
    when(userRepository.findByEmail(email)).thenReturn(Mono.just(savedUser));

    // Act
    Mono<String> sut = loginService.logUser(loginData);

    // Assert
    StepVerifier.create(sut).expectError(UnauthorizedException.class);
  }
}

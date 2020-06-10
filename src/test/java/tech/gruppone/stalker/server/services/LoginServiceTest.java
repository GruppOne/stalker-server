package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.exceptions.InvalidUserCredentialsException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class LoginServiceTest {

  @MockBean UserRepository userRepository;
  @MockBean JwtService jwtService;

  @Autowired LoginService loginService;

  @Test
  void testLogUser() {
    // Arrange
    final Long id = 1L;
    final String email = "email@email.email";
    final String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final LoginDataDto loginData = new LoginDataDto(email, password);

    final UserDao user = UserDao.builder().email(email).password(password).id(id).build();

    final String expectedEncodedJwt = "example-token";

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
    when(jwtService.createToken(id)).thenReturn(expectedEncodedJwt);

    // Act
    final Mono<String> sut = loginService.login(loginData.getEmail(), loginData.getPassword());

    // Assert
    StepVerifier.create(sut).expectNext(expectedEncodedJwt).verifyComplete();
  }

  @Test
  void testLogUserWithWrongPassword() {
    // Arrange
    final Long id = 1L;
    final String email = "email@email.email";
    final String savedPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2351";
    final String wrongPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final LoginDataDto loginData = new LoginDataDto(email, wrongPassword);

    final UserDao savedUser = UserDao.builder().email(email).password(savedPassword).id(id).build();
    when(userRepository.findByEmail(email)).thenReturn(Mono.just(savedUser));

    // Act
    final Mono<String> sut = loginService.login(loginData.getEmail(), loginData.getPassword());

    // Assert
    StepVerifier.create(sut).expectError(InvalidUserCredentialsException.class).verify();
  }
}

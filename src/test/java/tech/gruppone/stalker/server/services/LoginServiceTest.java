package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.configuration.JwtConfiguration;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
@Import(JwtConfiguration.class)
class LoginServiceTest {

  @Mock UserRepository userRepository;
  @Mock JwtService jwtService;

  @InjectMocks LoginService loginService;

  @Test
  void testLogUser() {
    // Arrange
    final Long id = 1L;
    final String email = "mario@gmail.com";
    final String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final LoginDataDto loginData = new LoginDataDto(email, password);

    final UserDao user = UserDao.builder().email(email).password(password).id(id).build();

    final String expectedEncodedJwt = "example-token";

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
    when(jwtService.createToken(id)).thenReturn(expectedEncodedJwt);

    // Act
    final Mono<String> sut = loginService.logUser(loginData.getEmail(), loginData.getPassword());

    // Assert
    StepVerifier.create(sut).expectNext(expectedEncodedJwt).verifyComplete();
  }

  @Test
  void testLogUserWithWrongPassword() {
    // Arrange
    final Long id = 1L;
    final String email = "mario@gmail.com";
    final String savedPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2351";
    final String wrongPassword =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    final LoginDataDto loginData = new LoginDataDto(email, wrongPassword);

    final UserDao savedUser = UserDao.builder().email(email).password(savedPassword).id(id).build();
    when(userRepository.findByEmail(email)).thenReturn(Mono.just(savedUser));

    // Act
    final Mono<String> sut = loginService.logUser(loginData.getEmail(), loginData.getPassword());

    // Assert
    StepVerifier.create(sut).expectError(UnauthorizedException.class).verify();
  }
}

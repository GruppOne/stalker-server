package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.security.JwtConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginServiceTest {

  @MockBean private UserRepository userRepository;
  @MockBean private JwtConfiguration jwtConfiguration;
  @Autowired private LoginService loginService;

  @Test
  public void testLogUser() {
    // Arrange
    String email = "mario@gmail.com";
    String password =
        "f853a81c51cdc3b75d5af43379965c56ff55a6fcf67d7cbc5daca8e1f7db01df46b768a35d8e472abda7a9ecc5bc46da4d56c4c85658ebd3557d6d08225a2352";

    UserDao user = UserDao.builder().email(email).password(password).id(1L).build();

    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

    LoginDataDto loginData = LoginDataDto.builder().email(email).password(password).build();

    String encoded = jwtConfiguration.createToken(1L);

    // Act
    Mono<String> sut = loginService.logUser(loginData);

    // Assertion
    StepVerifier.create(sut).expectNext(encoded);
  }
}

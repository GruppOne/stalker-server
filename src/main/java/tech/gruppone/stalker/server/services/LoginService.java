package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.security.JwtConfiguration;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

  UserRepository userRepository;
  JwtConfiguration jwtConfiguration;

  public void checkCredentials(UserDao user, SynchronousSink<String> sink, String password) {

    if (!password.equals(user.getPassword())) {
      sink.error(new UnauthorizedException());
    } else {
      sink.next(jwtConfiguration.createToken(user.getId()));
    }
  }

  public Mono<String> logUser(LoginDataDto loginData) {
    Mono<UserDao> userDao = userRepository.findByEmail(loginData.getEmail());
    var password = loginData.getPassword();
    return userDao.switchIfEmpty(Mono.error(new UnauthorizedException())).handle((user, sink) -> {
      checkCredentials(user, sink, password);
    });

  }
}

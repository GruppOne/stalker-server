package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
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


  public Mono<String> logUser(LoginDataDto loginData) {
    Mono<UserDao> userDao = userRepository.findByEmail(loginData.getEmail());
    return userDao.switchIfEmpty(Mono.error(new UnauthorizedException())).handle((user, sink) -> {
      if (!loginData.getPassword().equals(user.getPassword())) {
        sink.error(new UnauthorizedException());
      } else {
        sink.next(jwtConfiguration.createToken(user.getId()));
      }
    });

  }
}

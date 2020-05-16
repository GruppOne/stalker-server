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

;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

  private UserRepository userRepository;
  private JwtConfiguration jwtConfiguration;

  public Mono<String> logUser(LoginDataDto loginData) {
    Mono<UserDao> userLogging = userRepository.findByEmail(loginData.getEmail());

    return userLogging
        .map(
            (userDao) -> {
              if (loginData.getPassword().equals(userDao.getPassword())) {
                return jwtConfiguration.createToken(userDao.getId());
              } else {
                throw new UnauthorizedException();
              }
            })
        .switchIfEmpty(
            Mono.error(new UnauthorizedException()));
  }
}

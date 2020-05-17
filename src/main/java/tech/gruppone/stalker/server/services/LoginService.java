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

    return userDao
        .map(
            user -> {
              var expectedPassword = user.getPassword();

              if (!loginData.getPassword().equals(expectedPassword)) {
                // TODO this is the wrong way to throw from inside a map. should fix it eventually
                throw new UnauthorizedException();
              }

              return jwtConfiguration.createToken(user.getId());
            })
        // this is needed to handle the case where the findByEmail call returns no users
        .switchIfEmpty(Mono.error(UnauthorizedException::new));
  }
}

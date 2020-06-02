package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

  UserRepository userRepository;
  JwtService jwtService;

  public Mono<String> logUser(final String email, final String password) {
    final Mono<UserDao> userDao = userRepository.findByEmail(email);
    return userDao
        .switchIfEmpty(Mono.error(new UnauthorizedException()))
        .handle(
            (user, sink) -> {
              if (!password.equals(user.getPassword())) {
                sink.error(new UnauthorizedException());
              } else {
                sink.next(jwtService.createToken(user.getId()));
              }
            });
  }
}

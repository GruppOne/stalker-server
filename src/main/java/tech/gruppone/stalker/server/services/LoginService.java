package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.InvalidUserCredentialsException;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

  UserRepository userRepository;
  JwtService jwtService;

  public Mono<String> login(final String email, final String password) {

    return userRepository
        .findByEmail(email)
        .filter(user -> user.getPassword().equals(password))
        .switchIfEmpty(Mono.error(InvalidUserCredentialsException::new))
        .map(user -> jwtService.createToken(user.getId()));
  }
}

package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.model.api.LoginData;
import tech.gruppone.stalker.server.model.db.User;
import tech.gruppone.stalker.server.model.exceptions.LoginFailedException;
import tech.gruppone.stalker.server.repositories.RoleRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.model.api.UserRole;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginService {

  UserRepository userRepository;
  JwtService jwtService;

  public Mono<String> logUser(LoginDataDto loginData) {
    Mono<UserDao> userDao = userRepository.findByEmail(loginData.getEmail());
    return userDao.switchIfEmpty(Mono.error(new UnauthorizedException())).handle((user, sink) -> {
      if (!loginData.getPassword().equals(user.getPassword())) {
        sink.error(new UnauthorizedException());
      } else {
        sink.next(jwtService.createToken(user.getId()));
      }
    });
  }
}

package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UsersService {

  UserRepository userRepository;
  UserDataRepository userDataRepository;
  LoginService loginService;

  public Mono<String> signUpUser(final LoginDataDto loginDataDto, final UserDataDto userDataDto) {

    final var email = loginDataDto.getEmail();
    final var userDataEmail = userDataDto.getEmail();

    if (!email.equals(userDataEmail)) {
      return Mono.error(BadRequestException::new);
    }

    final var firstName = userDataDto.getFirstName();
    final var lastName = userDataDto.getLastName();

    // check if any field is blank
    if (email.isBlank() || firstName.isBlank() || lastName.isBlank()) {
      return Mono.error(BadRequestException::new);
    }

    final var password = loginDataDto.getPassword();

    if (password.length() != 128) {
      return Mono.error(BadRequestException::new);
    }

    // XXX should we check that the user is of legal age?
    final var birthDate = userDataDto.getBirthDate();

    final var userDao = UserDao.builder().email(email).password(password).build();

    final var userId =
        userRepository
            .save(userDao)
            .onErrorResume(e -> Mono.error(BadRequestException::new))
            .map(UserDao::getId);

    final var userDataDaoBuilder =
        UserDataDao.builder().firstName(firstName).lastName(lastName).birthDate(birthDate);

    final Mono<UserDataDao> insertedUserDataDao =
        userId.map(id -> userDataDaoBuilder.userId(id).build()).flatMap(userDataRepository::save);

    final Mono<String> jwtToken = loginService.logUser(email, password);

    return insertedUserDataDao.then(jwtToken);
  }
}

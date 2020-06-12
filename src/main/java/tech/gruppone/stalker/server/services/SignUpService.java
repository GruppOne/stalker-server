package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class SignUpService {

  DatabaseClient databaseClient;

  UserRepository userRepository;
  LoginService loginService;

  public Mono<String> createNewUser(
      final LoginDataDto loginDataDto, final UserDataDto userDataDto) {

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

    final var insertedUserDataDao =
        userId
            .map(id -> userDataDaoBuilder.userId(id).build())
            // must use dbclient to force an insert when the row id is not null
            .flatMap(
                userData -> databaseClient.insert().into(UserDataDao.class).using(userData).then());

    final Mono<String> jwtToken = loginService.login(email, password);

    return insertedUserDataDao.then(jwtToken);
  }
}

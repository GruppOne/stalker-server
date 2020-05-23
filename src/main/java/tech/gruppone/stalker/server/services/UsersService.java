package tech.gruppone.stalker.server.services;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
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
  JwtService jwtService;
  public Mono<String> signUpUser(UserDataWithLoginData signUp) {

    if ((!signUp.getLoginData().getEmail().isBlank())
      && (signUp.getLoginData().getPassword().length() == 128)
      && (!signUp.getUserData().getEmail().isBlank())
      && (!signUp.getUserData().getFirstName().isBlank())
      && (!signUp.getUserData().getLastName().isBlank())
      && (!signUp.getUserData().getBirthDate().toString().isBlank())){
      UserDao userDao =
        UserDao.builder()
          .email(signUp.getLoginData().getEmail())
          .password(signUp.getLoginData().getPassword())
          .build();
      Mono<Long> userId = userRepository.save(userDao).doOnError((error)->{throw new BadRequestException();}).map(UserDao::getId);
      var userDataDaoMono =
        userId.map(
          id ->
            UserDataDao.builder()
              .userId(id)
              .firstName(signUp.getUserData().getFirstName())
              .lastName(signUp.getUserData().getLastName())
              .birthDate(signUp.getUserData().getBirthDate())
              .build());

      var toInsert = userDataDaoMono.flatMap(
        userDataDao ->
          userDataRepository.insert(
            userDataDao.getUserId(),
            userDataDao.getFirstName(),
            userDataDao.getLastName(),
            userDataDao.getBirthDate()));
      Mono<String> jwtToken = userRepository.findByEmail(signUp.getLoginData().getEmail())
        .map(userDao1 -> jwtService.createToken(userDao1.getId()));
      return toInsert.then(jwtToken);
    }
    else{
      return Mono.error(new BadRequestException());
    }
  }

}

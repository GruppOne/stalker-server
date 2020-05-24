package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDataWithLoginData;
import tech.gruppone.stalker.server.model.api.UserDto;
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

  public Flux<UserDto> findAll() {

    return userRepository
        .findAll()
        .zipWith(userDataRepository.findAll())
        .map(
            result -> {
              var t1 = result.getT1();
              var t2 = result.getT2();
              return UserDto.builder()
                  .id(t1.getId())
                  .data(
                      UserDataDto.builder()
                          .email(t1.getEmail())
                          .firstName(t2.getFirstName())
                          .lastName(t2.getLastName())
                          .birthDate(t2.getBirthDate())
                          .creationDateTime(Timestamp.valueOf(t2.getCreatedDate()))
                          .build())
                  .build();
            });
  }

  public Mono<String> signUpUser(UserDataWithLoginData signUp) {

    if ((!signUp.getLoginData().getEmail().isBlank())
        && (signUp.getLoginData().getPassword().length() == 128)
        && (!signUp.getUserData().getEmail().isBlank())
        && (!signUp.getUserData().getFirstName().isBlank())
        && (!signUp.getUserData().getLastName().isBlank())
        && (signUp.getUserData().getBirthDate() != null)) {
      UserDao userDao =
          UserDao.builder()
              .email(signUp.getLoginData().getEmail())
              .password(signUp.getLoginData().getPassword())
              .build();
      Mono<Long> userId =
          userRepository
              .save(userDao)
              .onErrorResume(e -> Mono.error(new BadRequestException()))
              .map(UserDao::getId);
      var userDataDaoMono =
          userId.map(
              id ->
                  UserDataDao.builder()
                      .userId(id)
                      .firstName(signUp.getUserData().getFirstName())
                      .lastName(signUp.getUserData().getLastName())
                      .birthDate(signUp.getUserData().getBirthDate())
                      .build());

      var toInsert =
          userDataDaoMono.flatMap(
              userDataDao ->
                  userDataRepository.insert(
                      userDataDao.getUserId(),
                      userDataDao.getFirstName(),
                      userDataDao.getLastName(),
                      userDataDao.getBirthDate()));
      Mono<String> jwtToken =
          userRepository
              .findByEmail(signUp.getLoginData().getEmail())
              .map(userDao1 -> jwtService.createToken(userDao1.getId()));
      return toInsert.then(jwtToken);
    } else {
      return Mono.error(new BadRequestException());
    }
  }
}

package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
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

  public Mono<String> signUpUser(LoginDataDto loginDataDto, UserDataDto userDataDto) {

    if ((!loginDataDto.getEmail().isBlank())
        && (loginDataDto.getPassword().length() == 128)
        && (!userDataDto.getEmail().isBlank())
        && (!userDataDto.getFirstName().isBlank())
        && (!userDataDto.getLastName().isBlank())
        && (userDataDto.getBirthDate() != null)) {
      UserDao userDao =
          UserDao.builder()
              .email(loginDataDto.getEmail())
              .password(loginDataDto.getPassword())
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
                      .firstName(userDataDto.getFirstName())
                      .lastName(userDataDto.getLastName())
                      .birthDate(userDataDto.getBirthDate())
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
              .findByEmail(loginDataDto.getEmail())
              .map(userDao1 -> jwtService.createToken(userDao1.getId()));
      return toInsert.then(jwtToken);
    } else {
      return Mono.error(new BadRequestException());
    }
  }
}

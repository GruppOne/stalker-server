package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

  Clock clock;

  UserRepository userRepository;
  UserDataRepository userDataRepository;

  private UserDto fromDaosTuple(final Tuple2<UserDao, UserDataDao> tuple) {
    final var userDao = tuple.getT1();
    final var userDataDao = tuple.getT2();

    final var userDataDto =
        UserDataDto.builder()
            .email(userDao.getEmail())
            .firstName(userDataDao.getFirstName())
            .lastName(userDataDao.getLastName())
            .birthDate(userDataDao.getBirthDate())
            .creationDateTime(Timestamp.valueOf(userDataDao.getCreatedDate()))
            .build();

    return new UserDto(userDao.getId(), userDataDto);
  }

  private Flux<Tuple2<UserDao, UserDataDao>> mergeUsersWithTheirUserData(
      final Flux<UserDao> userDaos, final Flux<UserDataDao> userDataDaos) {

    return userDaos
        // this could be refactored to an utility function
        .flatMap(
        // zip an userDao with the userDataDao that has the same id
        userDao -> {
          final Mono<UserDataDao> matchedUserDataDao =
              userDataDaos
                  .filter(userDataDao -> userDataDao.getUserId().equals(userDao.getId()))
                  .doOnNext(
                      found -> log.info("found matching userDataDao with id {}", found.getUserId()))
                  .next();

          return Mono.just(userDao).zipWith(matchedUserDataDao);
        });
  }

  public Flux<UserDto> findAll() {
    final var userDaos = userRepository.findAll();
    final var userDataDaos = userDataRepository.findAll();

    return mergeUsersWithTheirUserData(userDaos, userDataDaos).map(this::fromDaosTuple);
  }

  public Flux<UserDto> findAllById(final Flux<Long> ids) {
    final var userDaos = userRepository.findAllById(ids);
    final var userDataDaos = userDataRepository.findAllById(ids);

    return mergeUsersWithTheirUserData(userDaos, userDataDaos).map(this::fromDaosTuple);
  }

  public Mono<UserDto> findById(final long userId) {

    return userRepository
        .findById(userId)
        .zipWith(userDataRepository.findById(userId))
        .map(this::fromDaosTuple);
  }

  public Mono<Void> updatePassword(
      final String oldPassword, final String newPassword, final Long userId) {

    if (newPassword.isBlank()) {
      // TODO this exception being thrown by the endpoint is not documented in the API
      return Mono.error(BadRequestException::new);
    }

    return userRepository
        .findById(userId)
        .filter(userDao -> userDao.getPassword().equals(oldPassword))
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .flatMap(userDao -> userRepository.save(userDao.withPassword(newPassword)))
        .then();
  }

  public Mono<Void> updateUserById(final UserDataDto userDataDto, final Long userId) {
    // XXX this does not update a user's email (not required).

    return userRepository
        .findById(userId)
        .filter(userDao -> userDao.getEmail().equals(userDataDto.getEmail()))
        .switchIfEmpty(Mono.error(BadRequestException::new))
        .map(UserDao::getId)
        .flatMap(userDataRepository::findById)
        .flatMap(
            oldUserDataDao -> {
              final var createdDate = oldUserDataDao.getCreatedDate();

              final UserDataDao updatedUserDataDao =
                  UserDataDao.builder()
                      .userId(userId)
                      .firstName(userDataDto.getFirstName())
                      .lastName(userDataDto.getLastName())
                      .birthDate(userDataDto.getBirthDate())
                      .createdDate(createdDate)
                      .lastModifiedDate(LocalDateTime.now(clock))
                      .build();

              return userDataRepository.save(updatedUserDataDao);
            })
        .then();
  }
}

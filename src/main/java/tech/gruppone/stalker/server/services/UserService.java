package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
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
public class UserService {

  Clock clock;

  UserRepository userRepository;
  UserDataRepository userDataRepository;

  public Mono<UserDto> findById(final long userId) {

    return userRepository
        .findById(userId)
        .zipWith(userDataRepository.findById(userId))
        .map(
            result -> {
              final var t1 = result.getT1();
              final var t2 = result.getT2();

              final var userDataDto =
                  UserDataDto.builder()
                      .email(t1.getEmail())
                      .firstName(t2.getFirstName())
                      .lastName(t2.getLastName())
                      .birthDate(t2.getBirthDate())
                      .creationDateTime(Timestamp.valueOf(t2.getLastModifiedDate()))
                      .build();

              return new UserDto(t1.getId(), userDataDto);
            });
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
                          .build()).build();
            });
  }


}

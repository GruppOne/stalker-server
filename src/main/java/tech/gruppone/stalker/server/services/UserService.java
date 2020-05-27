package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

  UserRepository userRepository;
  UserDataRepository userDataRepository;

  public Mono<UserDto> findById(final long userId) {

    return userRepository.findById(userId).zipWith(userDataRepository.findById(userId)).map(result -> {
      var t1 = result.getT1();
      var t2 = result.getT2();
      return UserDto.builder()
        .id(t1.getId())
        .data(UserDataDto.builder()
          .email(t1.getEmail())
          .firstName(t2.getFirstName())
          .lastName(t2.getLastName())
          .birthDate(t2.getBirthDate())
          .creationDateTime(Timestamp.valueOf(t2.getLastModifiedDate()))
          .build())
        .build();
    });
  }

  public Mono<Void> updatePassword(
      final String oldPassword, final String newPassword, final Long userId) {

    return userRepository.findById(userId)
      .filter(userDao -> userDao.getPassword().equals(updatePasswordDto.getOldPassword())
        && !(updatePasswordDto.getNewPassword().isBlank()))
      .switchIfEmpty(Mono.error(new NotFoundException()))
      .flatMap(userDao -> userRepository.save(UserDao.builder()
        .id(userDao.getId())
        .email(userDao.getEmail())
        .password(updatePasswordDto.getNewPassword())
        .build()))
      .then();
  }

  public Mono<Void> putUserById(UserDataDto userDataDto, Long userId) {
    return userRepository.findById(userId)
      .filter(userDao -> userDao.getEmail().equals(userDataDto.getEmail()))
      .switchIfEmpty(Mono.error(new NotFoundException()))
      .and(userDataRepository.save(UserDataDao.builder()
        .userId(userId)
        .firstName(userDataDto.getFirstName())
        .lastName(userDataDto.getLastName())
        .birthDate(userDataDto.getBirthDate())
        .createdDate(LocalDateTime.now())
        .lastModifiedDate(LocalDateTime.now())
        .build()))
      .then();
  }
}

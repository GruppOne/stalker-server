package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.UserDataDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ConnectionService {
  ConnectionRepository connectionRepository;
  UserRepository userRepository;
  UserDataRepository userDataRepository;

  public Flux<UserDto> findConnectedUsersByOrganizationId(final Long organizationId) {

    final var connectedUserIds =
        connectionRepository.findConnectedUserIdsByOrganizationId(organizationId);

    final var connectedUserDaos = userRepository.findAllById(connectedUserIds);
    final var connectedUserDataDaos = userDataRepository.findAllById(connectedUserIds);

    return connectedUserDaos
        // this could be refactored to an utility function
        .flatMap(
            // zip an userDao with the userDataDao that has the same id
            connectedUserDao -> {
              final Mono<UserDataDao> connectedUserDataDao =
                  connectedUserDataDaos
                      .filter(
                          userDataDao -> userDataDao.getUserId().equals(connectedUserDao.getId()))
                      .doOnNext(
                          found ->
                              log.info("found matching userDataDao with id {}", found.getUserId()))
                      .next();

              return Mono.just(connectedUserDao).zipWith(connectedUserDataDao);
            })
        .map(
            tuple -> {
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
            });
  }
}

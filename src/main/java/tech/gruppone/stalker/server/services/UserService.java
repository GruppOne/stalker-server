package tech.gruppone.stalker.server.services;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Value
@NonFinal
@Service
public class UserService {

  @NonNull UserRepository userRepository;
  @NonNull UserDataRepository userDataRepository;

  public Mono<UserDto> findById(final long userId) {

    return userRepository
        .findById(userId)
        .zipWith(userDataRepository.findById(userId))
        .map(
            result -> {
              var t1 = result.getT1();
              var t2 = result.getT2();
              return UserDto.builder()
                  .id(t1.getId())
                  .userData(
                      UserDataDto.builder()
                          .email(t1.getEmail())
                          .firstName(t2.getFirstName())
                          .lastName(t2.getLastName())
                          .birthDate(t2.getBirthDate())
                          .creationDateTime(t2.getLastModifiedDate())
                          .build())
                  .build();
            });
  }
}

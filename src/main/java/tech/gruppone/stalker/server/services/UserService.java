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

  public Mono<UserDto> read(final Long userId){

// IT'S VERY VERBOSE!!!
return userRepository.findById(userId).zipWith(userDataRepository.findById(userId)).map( result -> {
    return UserDto.builder().id(result.getT1().getId()).userData(UserDataDto.builder().email(result.getT1().getEmail()).firstName(result.getT2().getFirstName())
    .lastName(result.getT2().getLastName()).birthDate(result.getT2().getBirthDate()).creationDateTime(result.getT2().getLastModifiedDate()).build()).build();
    });

  }

   // Mono<UserDao> userResult = userRepository.findById(userId);
    //Mono<UserDataDao> userDataResult = userDataRepository.findById(userId);

    // HOW TO USE MAP METHOD?? IS IT POSSIBLE TO MAKE A LIST TO MAPPING ALL THE MONO CONTENT??
    //final UserDataDto userData = UserDataDto.builder()
    //    .email(null)
    //    .firstName(null)
    //    .lastName(null)
    //    .birthDate(null)
    //    .creationDateTime(null)
    //    .build();

    //final UserDto user = UserDto.builder().id(userId).userData(userData).build();

    //return Mono.just(user);


}

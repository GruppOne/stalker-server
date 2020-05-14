package tech.gruppone.stalker.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.LoginFailedException;
import tech.gruppone.stalker.server.model.api.EncodedJwtDto;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Service
public class LoginService {


  @Autowired
  private UserRepository userRepository;


  @ResponseStatus(code = HttpStatus.CREATED)
  public Mono<EncodedJwtDto> logUser(LoginDataDto loginData){
    Mono<UserDao> userLogging = userRepository.findByEmail(loginData.getEmail());

    return userLogging.map((userDao) -> {
      if(loginData.getPassword().equals(userDao.getPassword())){
        return EncodedJwtDto.builder().encodedJwt("provaJwt").build();
      }
      else{
        throw new LoginFailedException("Combinazione di username e password errata");
      }
    }).switchIfEmpty(Mono.error(new LoginFailedException("Combinazione di username e password errata")));
  }


}

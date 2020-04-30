package tech.gruppone.stalker.server.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.UnauthenticatedUser;
import tech.gruppone.stalker.server.repository.UserRepository;
import tech.gruppone.stalker.server.security.JwtUtil;

@Service
public class LoginService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  public Mono<ResponseEntity<?>> logUser(UnauthenticatedUser unauthenticatedUser){
    Map<String, Object> x = new HashMap<>();
    x.put("role", "manager");
    return userRepository.findByEmail(unauthenticatedUser.getEmail()).map((user) -> {
      if(unauthenticatedUser.getPassword().equals(user.getPassword())){
        return ResponseEntity.ok(jwtUtil.createToken(user.getEmail(), x));
      }
      else{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
}

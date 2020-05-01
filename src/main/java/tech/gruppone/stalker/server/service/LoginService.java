package tech.gruppone.stalker.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.UnauthenticatedUser;
import tech.gruppone.stalker.server.repository.RoleRepository;
import tech.gruppone.stalker.server.repository.UserRepository;
import tech.gruppone.stalker.server.security.JwtUtil;
import tech.gruppone.stalker.server.model.UserRoles;

@Service
public class LoginService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  public Mono<ResponseEntity<?>> logUser(UnauthenticatedUser unauthenticatedUser){
    List<UserRoles> userRoles = getRolesForLogging(unauthenticatedUser);
    Map<String, List<UserRoles>> roles = new HashMap<>();
    roles.put("organizations", userRoles);
    return userRepository.findByEmail(unauthenticatedUser.getEmail()).map((user) -> {
      if(unauthenticatedUser.getPassword().equals(user.getPassword())){
        return ResponseEntity.ok(jwtUtil.createToken(user.getId(), user.getEmail(), roles));
      }
      else{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  public List<UserRoles> getRolesForLogging(UnauthenticatedUser user){
    final  List<UserRoles> userRolesList = new ArrayList<>();
    roleRepository.findUserRoles(user.getEmail()).subscribe(userRoles -> {userRolesList.add(userRoles);});
    return userRolesList;
  }
}

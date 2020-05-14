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
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.db.UserDao;
import tech.gruppone.stalker.server.repositories.RoleRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.model.api.UserRoleDto;

@Service
public class LoginService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @ResponseStatus(code = HttpStatus.CREATED)
  public Mono<String> logUser(LoginDataDto loginData){
    Map<String, List<UserRoleDto>> rolesForLogging = getRolesForLogging(loginData);
     /*userRepository.findByEmail(loginData.getEmail()).map((user) -> {
      if(loginData.getPassword().equals(user.getPassword())){
        return Mono.just(jwtUtil.createToken(user.getId(), user.getEmail(), rolesForLogging));
      }
      else{
        throw new LoginFailedException();
      }
    }).defaultIfEmpty(throw new LoginFailedException());*/
    Mono<UserDao> userLogging = userRepository.findByEmail(loginData.getEmail());
    return userLogging.map((user) -> {
      if(loginData.getPassword().equals(user.getPassword())){
        return (jwtUtil.createToken(user.getId(), user.getEmail(), rolesForLogging));
      }
      else{
        throw new LoginFailedException("Combinazione di username e password errata");
      }
    });
  }



  private Map<String, List<UserRoleDto>> getRolesForLogging(LoginDataDto user){
    List<UserRoleDto> userRoleList = new ArrayList<>();
    roleRepository.findUserRoles(user.getEmail()).subscribe(userRole -> {
      userRoleList.add(userRole);
    });
    Map<String, List<UserRoleDto>> roleMap = new HashMap<>();
    roleMap.put("organizationRoles", userRoleList);
    return roleMap;
  }
}

package tech.gruppone.stalker.server.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.OrganizationRole;
import tech.gruppone.stalker.server.model.UnauthenticatedUser;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repository.ProvaRepository;
import tech.gruppone.stalker.server.repository.RoleRepository;
import tech.gruppone.stalker.server.repository.UserRepository;
import tech.gruppone.stalker.server.security.JwtUtil;
import tech.gruppone.stalker.server.security.UserRoles;

@RestController
@RequestMapping("/users")
@Data
public class UserController {

  @Autowired
  @Getter(AccessLevel.NONE)
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  ProvaRepository provaRepository;

  @Autowired
  JwtUtil util;

  @GetMapping
  public Flux<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable Long id) {

    return userRepository.findById(id);
  }

  // TODO implement
  // TODO should return valid json
  @GetMapping("/{id}/subscribed")
  public Flux<Integer> getSubscribedOrganizations(ServerHttpResponse response,@PathVariable Long id) {

    response.setStatusCode(HttpStatus.NOT_IMPLEMENTED);

    return Flux.empty();
  }

  // fake login. It is used only to verify if login endpoint retrieves a token
  @PostMapping("/login")
  public Mono<ResponseEntity<?>> login(@RequestBody UnauthenticatedUser unauthenticatedUser){
     Map<String, Object> x = new HashMap<>();
     x.put("role", "manager");
     return userRepository.findByEmail(unauthenticatedUser.getEmail()).map((user) -> {
        if(unauthenticatedUser.getPassword().equals(user.getPassword())){
          return ResponseEntity.ok(util.createToken(user.getEmail(), x));
        }
        else{
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
  @GetMapping("/roles/{username}")
  public Flux<OrganizationRole> find (@PathVariable String username){
     //return roleRepository.findbyUser(username);
    return provaRepository.showAll();
  }
}

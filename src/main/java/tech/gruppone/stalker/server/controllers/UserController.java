package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
<<<<<<< HEAD
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
=======
import lombok.Data;
=======
>>>>>>> 0dd57b4... fix: fix some errors
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> f8cb0d3... feat: try authorization
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import org.springframework.web.server.ServerWebExchange;
=======
>>>>>>> 0dd57b4... fix: fix some errors
import reactor.core.publisher.Flux;
>>>>>>> f8cb0d3... feat: try authorization
import reactor.core.publisher.Mono;
<<<<<<< HEAD
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.services.UserService;
import tech.gruppone.stalker.server.model.Connection;
import tech.gruppone.stalker.server.model.User;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.RoleRepository;
=======
import tech.gruppone.stalker.server.model.db.User;
>>>>>>> 0dd57b4... fix: fix some errors
import tech.gruppone.stalker.server.repositories.UserRepository;
import tech.gruppone.stalker.server.services.JwtUtil;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}")
public class UserController {

<<<<<<< HEAD
  // FIXME should not depend on both repo and service
=======
// find a solution. It is not possible abuse this field in every controller class
  @Autowired
  JwtUtil util;

  @Autowired
>>>>>>> 0dd57b4... fix: fix some errors
  UserRepository userRepository;
  UserService userService;


<<<<<<< HEAD
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserDto> getUserById(@PathVariable final Long userId) {

    return userService.findById(userId);
=======
  @GetMapping("/{id}")
  public Mono<User> getUserById(@PathVariable Long id) {

    return userRepository.findById(id);
>>>>>>> 0dd57b4... fix: fix some errors
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserById(@PathVariable final Long userId) {

    return userRepository.deleteUserById(userId);

  }
}

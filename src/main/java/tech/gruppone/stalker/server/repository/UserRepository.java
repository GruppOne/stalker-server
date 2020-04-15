package tech.gruppone.stalker.server.repository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;

public class UserRepository {

  private User user = User.builder().email("mario.rossi@gmail.com").password("HASHED_PASSWORD").build();

  public Mono<User> findByEmail(String email) {
    return Mono.just(user);
  }

}

package tech.gruppone.stalkerserver.user;

import reactor.core.publisher.Mono;

public class UserRepository {

  private User user = User.builder().email("mario.rossi@gmail.com").password("HASHED_PASSWORD").build();

  public Mono<User> findByEmail(String email) {
    return Mono.just(user);
  }

}

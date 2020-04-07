package tech.gruppone.stalkerserver.user;

import reactor.core.publisher.Mono;

public class UserRepository {

  private User user = User.builder().email("mario.rossi@example.com").password("hashedPassword").build();

  public Mono<User> findByEmail(String email) {
    return Mono.just(user);
  }

}

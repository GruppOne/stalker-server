package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDao;

public interface UserRepository extends ReactiveCrudRepository<UserDao, Long> {
  Mono<UserDao> findByEmail(final String email);

  @Query(
      "SELECT * FROM User WHERE email = :email; UPDATE User u SET u.password = :newHashedPassword WHERE email = :email")
  public Mono<UserDao> findByEmailAndUpdatePassword(String email, String newHashedPassword);
}

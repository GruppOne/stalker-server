
package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDao;


public interface UserRepository extends ReactiveCrudRepository<UserDao, Long> {

  Mono<UserDao> findById(Long id);

  public Flux<UserDao> findAll();

  public Mono<UserDao> findByEmail(String email);

  @Modifying
  // TODO this might be unnecessary
  @Query(
    "DELETE Users, UserData FROM Users INNER JOIN UserData WHERE Users.id = UserData.userId AND UserData.userId = :id")
  Mono<Void> deleteUserById(Long id);
}

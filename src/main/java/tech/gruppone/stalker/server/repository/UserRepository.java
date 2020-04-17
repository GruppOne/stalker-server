package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {

  @Query("select * from Users")
  public Flux<User> findAll();

  @Query("select * from Users u where u.id = :id")
  public Mono<User> findById(Long id);

  @Query("select * from Users u where u.email = :email")
  public Mono<User> findByEmail(String email);

}

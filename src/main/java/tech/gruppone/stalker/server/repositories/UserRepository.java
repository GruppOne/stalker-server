package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDao;

public interface UserRepository extends ReactiveCrudRepository<UserDao, Long> {

  public Mono<UserDao> findById(Long id);

  @Query("select * from Users")
  public Flux<UserDao> findAll();


  @Query("select * from User u where u.email = :email")
  public Mono<UserDao> findByEmail(String email);

  @Modifying
  @Query("DELETE Users, UserData FROM Users INNER JOIN UserData WHERE Users.id = UserData.userId AND UserData.userId = :id")
  public Mono<Void> deleteUserById(Long id);

}

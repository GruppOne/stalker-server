package tech.gruppone.stalker.server.repositories;

import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDao;

public interface UserRepository extends ReactiveCrudRepository<UserDao,Long> {

  // @Query("select * from Users")
  // public Flux<UserDao> findAll();

  @Query("SELECT id, email from User where id = :id")
  public Mono<UserDao> findById(Long id);

  // @Query("SELECT id, email, firstName, lastName, birthDate, createdDate FROM Users u, UserData d WHERE u.id = d.userId AND u.id = :id")
  // public Mono<User> findById(Long id);

  // @Query("select * from Users u where u.email = :email")
  // public Mono<UserDao> findByEmail(String email);

  // @Modifying
  // @Query("UPDATE UserData u SET u.firstName = :firstName, u.lastName = :lastName, u.birthDate = :birthdate, u.lastModifiedDate = now() WHERE u.userId = :id")
  // public Mono<Void> updateUser(String firstName, String lastName, LocalDate birthdate, Long id);

  @Modifying
  @Query("DELETE Users, UserData FROM Users INNER JOIN UserData WHERE Users.id = UserData.userId AND UserData.userId = :id")
  public Mono<Void> deleteUserById(Long id);

}

package tech.gruppone.stalker.server.repository;

import java.lang.annotation.Repeatable;
import java.time.LocalDate;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {

  @Query("select * from Users")
  public Flux<User> findAll();

  @Modifying
  @Query("insert into Users (email, password) values (:email, :password)")
  //@Query("insert into UserData (userId) select id from Users order by createdDate DESC limit 1")
  public Mono<User> createUser(String email, String password);

  @Query("select * from Users u where u.id = :id")
  public Mono<User> findById(Long id);

  @Modifying
  @Query("update UserData u set u.firstName = :firstName, u.lastName = :lastName where u.userId = :id")
  public Mono<User> updateUserData(Long id, String firstName, String lastName/*, LocalDate birthdate*/);

  @Query("select * from Users u where u.email = :email")
  public Mono<User> findByEmail(String email);

}

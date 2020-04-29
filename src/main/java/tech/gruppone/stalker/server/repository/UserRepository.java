package tech.gruppone.stalker.server.repository;

import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.User;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {

  @Query("select * from Users left join UserData on Users.id = UserData.userId")
  public Flux<User> findAll();

  @Query("select * from Users u where u.id = :id")
  public Mono<User> findById(Long id);

  @Query("select count(*) from Users where email = ':email'")
  public Mono<Integer> findByEmail(String email);

  @Modifying
  @Query("insert into Users (email, password) values (:email, :password); insert into UserData (userId, firstName, lastName, birthDate) values (LAST_INSERT_ID(), :firstname, :lastname, :birthdate)")
  public Mono<User> createUser(String email, String password, String firstname, String lastname, LocalDate birthdate);

  @Modifying
  @Query("update UserData u set u.firstName = :firstName, u.lastName = :lastName, u.birthDate = :birthdate, u.lastModifiedDate = now() where u.userId = :id")
  public Mono<User> updateUser(String firstName, String lastName, LocalDate birthdate, Long id);

  @Query("delete Users,UserData from Users inner join UserData where Users.id = UserData.userId and UserData.userId = :id")
  public Mono<Void> deleteUserById(Long id);

}

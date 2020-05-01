package tech.gruppone.stalker.server.repositories;

import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.models.api.User;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {

  @Query("SELECT id, email, firstName, lastName, birthDate, createdDate FROM Users u, UserData d WHERE u.id = d.userId")
  public Flux<User> findAllUsers();

  @Query("SELECT * FROM Users u WHERE u.id = :id")
  public Mono<User> findById(Long id);

  @Query("SELECT COUNT(*) FROM Users WHERE email = ':email'")
  public Mono<String> findByEmail(String email);

  @Query("SELECT id FROM Users WHERE email = ':email'")
  public Mono<User> findIdByMail(String email);

  @Modifying
  @Query("INSERT INTO Users (email, password) VALUES (:email, :password); INSERT INTO UserData (userId, firstName, lastName, birthDate) VALUES (LAST_INSERT_ID(), :firstname, :lastname, :birthdate)")
  public Mono<User> createUser(String email, String password, String firstname, String lastname, LocalDate birthdate);

  @Modifying
  @Query("UPDATE UserData u SET u.firstName = :firstName, u.lastName = :lastName, u.birthDate = :birthdate, u.lastModifiedDate = now() WHERE u.userId = :id")
  public Mono<User> updateUser(String firstName, String lastName, LocalDate birthdate, Long id);

  @Modifying
  @Query("DELETE Users, UserData FROM Users INNER JOIN UserData WHERE Users.id = UserData.userId AND UserData.userId = :id")
  public Mono<Void> deleteUserById(Long id);

}

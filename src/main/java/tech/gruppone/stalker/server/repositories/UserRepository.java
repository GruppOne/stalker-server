package tech.gruppone.stalker.server.repositories;

import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserDto;

public interface UserRepository extends ReactiveCrudRepository<UserDto,Long> {

  @Query("select * from Users")
  public Flux<UserDto> findAll();

  @Query("select * from Users u where u.id = :id")
  public Mono<UserDto> findById(Long id);

  // @Query("SELECT id, email, firstName, lastName, birthDate, createdDate FROM Users u, UserData d WHERE u.id = d.userId AND u.id = :id")
  // public Mono<User> findById(Long id);

  @Query("select * from Users u where u.email = :email")
  public Mono<UserDto> findByEmail(String email);

  @Modifying
  @Query("UPDATE UserData u SET u.firstName = :firstName, u.lastName = :lastName, u.birthDate = :birthdate, u.lastModifiedDate = now() WHERE u.userId = :id")
  public Mono<Void> updateUser(String firstName, String lastName, LocalDate birthdate, Long id);

  @Modifying
  @Query("DELETE Users, UserData FROM Users INNER JOIN UserData WHERE Users.id = UserData.userId AND UserData.userId = :id")
  public Mono<Void> deleteUserById(Long id);

}

package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDataDao;

public interface UserDataRepository extends ReactiveCrudRepository<UserDataDao, Long> {

  @Query("SELECT * FROM UserData WHERE userId = :id")
  public Mono<UserDataDao> findById(Long id);
}

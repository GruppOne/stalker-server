package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDataDao;

public interface UserDataRepository extends ReactiveCrudRepository<UserDataDao, Long> {

  public Mono<UserDataDao> findById(Long id);
}

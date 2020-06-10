package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.UserDao;

public interface UserRepository extends ReactiveCrudRepository<UserDao, Long> {
  Mono<UserDao> findByEmail(final String email);
}

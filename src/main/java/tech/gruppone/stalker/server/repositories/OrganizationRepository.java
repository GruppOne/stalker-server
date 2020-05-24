package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.OrganizationDao;

public interface OrganizationRepository extends ReactiveCrudRepository<OrganizationDao, Long> {

  Flux<OrganizationDao> findAll();

  Mono<OrganizationDao> findById(final long id);

}

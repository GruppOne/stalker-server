package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.db.PlaceDao;

public interface PlaceRepository extends ReactiveCrudRepository<PlaceDao, Long> {

  @Query("SELECT * FROM Place WHERE organizationId = :organizationId")
  Flux<PlaceDao> findAllByOrganizationId(final long organizationId);
}

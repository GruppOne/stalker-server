package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.Organization;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization,Long> {

  @Query("select * from Organization")
  public Flux<Organization> findAll();

  @Query("select * from Organization o where o.id = :id")
  public Mono<Organization> findById(Long id);
}

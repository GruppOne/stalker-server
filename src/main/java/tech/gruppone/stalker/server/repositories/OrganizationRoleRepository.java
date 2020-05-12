package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

public interface OrganizationRoleRepository extends ReactiveCrudRepository<OrganizationRole, Long> {

  @Override
  @Query("SELECT * FROM OrganizationRole")
  public Flux<OrganizationRole> findAll();
}

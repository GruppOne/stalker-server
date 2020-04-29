package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.OrganizationData;

public interface ConnectionRepository extends ReactiveCrudRepository<OrganizationData, Long> {

  @Query("select o.id, o.name, o.description from Connections c, Organizations o where c.organizationId = o.id and c.userId = :id")
  public Flux<OrganizationData> findConnectedOrganizationsByUserId(Long id);
}

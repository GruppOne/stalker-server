package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.models.api.OrganizationData;

public interface ConnectionRepository extends ReactiveCrudRepository<OrganizationData, Long> {

  @Query("SELECT o.id, o.name, o.description FROM Connections c, Organizations o WHERE c.organizationId = o.id AND c.userId = :id")
  public Flux<OrganizationData> findConnectedOrganizationsByUserId(Long id);
}

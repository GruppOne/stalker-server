package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.models.api.AppConnection;

public interface ConnectionsRepository extends ReactiveCrudRepository<AppConnection, Long> {

  @Modifying
  @Query("INSERT INTO Connections (organizationId, userId) VALUES (:organizationId, :userId)")
  public Mono<AppConnection> postUserToOrganizationConnection(Long organizationId, Long userId);

  @Modifying
  @Query("DELETE FROM Connections WHERE organizationId = :organizationId AND userId = :id")
  public Mono<Void> deleteUserToOrganizationConnection(Long organizationId, Long userId);

}

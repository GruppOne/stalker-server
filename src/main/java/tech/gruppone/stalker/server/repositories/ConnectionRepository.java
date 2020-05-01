package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.AppConnection;
import tech.gruppone.stalker.server.model.api.responses.GetUserIdOrganizationsConnectionsResponse;

public interface ConnectionRepository extends ReactiveCrudRepository<AppConnection, Long> {

  @Query("SELECT o.id FROM Connections c, Organizations o WHERE c.organizationId = o.id AND c.userId = :id")
  public Flux<GetUserIdOrganizationsConnectionsResponse> findConnectedOrganizationsByUserId(Long id);

  @Modifying
  @Query("INSERT INTO Connections (organizationId, userId) VALUES (:organizationId, :userId)")
  public Mono<Void> postUserToOrganizationConnection(Long organizationId, Long userId);

  @Modifying
  @Query("DELETE FROM Connections WHERE organizationId = :organizationId AND userId = :id")
  public Mono<Void> deleteUserToOrganizationConnection(Long organizationId, Long userId);

}

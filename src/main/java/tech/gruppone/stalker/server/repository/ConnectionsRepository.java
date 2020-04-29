package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.AppConnection;

public interface ConnectionsRepository extends ReactiveCrudRepository<AppConnection, Long> {

  @Modifying
  @Query("insert into Connections (organizationId, userId) values (:organizationId, :userId)")
  public Mono<AppConnection> postUserToOrganizationConnection(Long organizationId, Long userId);

  @Modifying
  @Query("delete from Connections where organizationId = :organizationId and userId = :id ")
  public Mono<Void> deleteUserToOrganizationConnection(Long organizationId, Long userId);

}

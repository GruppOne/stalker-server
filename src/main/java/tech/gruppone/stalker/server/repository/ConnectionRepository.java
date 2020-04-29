package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Connection;

public interface ConnectionRepository extends ReactiveCrudRepository<Connection, Long> {

  @Modifying
  @Query("insert into Connections (organizationId, userId) values (:organizationId, :userId)")
  public Mono<Connection> createConnectionById(Long organizationId, Long userId);

  @Modifying
  @Query("delete from Connections where organizationId = :organizationId and userId = :id ")
  public Mono<Void> deleteConnectionById(Long organizationId, Long userId);

}

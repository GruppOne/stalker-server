package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.ConnectionDao;

public interface ConnectionRepository extends ReactiveCrudRepository<ConnectionDao, Long> {

  @Query("INSERT INTO Connection (userId, organizationId) VALUES (:userId, :organizationId)")
  Mono<Void> createUserConnection(
      @Param("userId") long userId, @Param("organizationId") long organizationId);

  @Query("DELETE FROM Connection WHERE userId = :userId AND organizationId = :organizationId")
  Mono<Void> deleteUserConnection(
      @Param("userId") long userId, @Param("organizationId") long organizationId);

  @Query(
      "SELECT c.organizationId FROM Connection c, Organization o WHERE c.organizationId = o.id AND c.userId = :id")
  Flux<Long> findConnectedOrganizationsByUserId(Long id);
}

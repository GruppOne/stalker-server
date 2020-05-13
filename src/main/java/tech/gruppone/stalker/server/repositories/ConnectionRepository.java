package tech.gruppone.stalker.server.repositories;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.ConnectionDao;

public interface ConnectionRepository extends ReactiveCrudRepository<ConnectionDao,Long> {


  @Query("INSERT INTO Connection (userId, organizationId, createdDate) VALUES (:userId, :organizationId, :createdTime)")
  Mono<Void> createUserConnection(@Param("userId") long userId, @Param("organizationId") long organizationId, @Param("createdTime") LocalDateTime createdTime);

  @Query("DELETE FROM Connection WHERE userId = :userId AND organizationId = :organizationId")
  Mono<Void> deleteUserConnection(@Param("userId") long userId,@Param("organizationId")long organizationId);
}

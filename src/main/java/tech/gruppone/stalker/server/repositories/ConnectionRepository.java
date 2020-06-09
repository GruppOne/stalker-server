package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface ConnectionRepository extends ReactiveCrudRepository<ConnectionDao, Long> {

  @Query("SELECT userId FROM Connection WHERE organizationId = :id")
  Flux<Long> findConnectedUserIdsByOrganizationId(Long id);

  @Query("SELECT organizationId FROM Connection WHERE userId = :id")
  Flux<Long> findConnectedOrganizationIdsByUserId(Long id);

  @Query("SELECT * FROM LdapConfiguration WHERE organizationId = :organizationId")
  Mono<LdapConfigurationDao> getLdapById(@Param("organizationId") long organizationId);

  @Query("INSERT INTO Connection (userId, organizationId) VALUES (:userId, :organizationId)")
  Mono<Void> createUserConnection(
      @Param("userId") long userId, @Param("organizationId") long organizationId);

  @Query("DELETE FROM Connection WHERE userId = :userId AND organizationId = :organizationId")
  Mono<Void> deleteByUserIdAndOrganizationId(
      @Param("userId") long userId, @Param("organizationId") long organizationId);
}

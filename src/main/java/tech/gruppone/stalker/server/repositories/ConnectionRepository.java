package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface ConnectionRepository extends ReactiveCrudRepository<ConnectionDao, Long> {

  // the annotation is needed because the derived query does not work (??)
  @Query("DELETE FROM Connection WHERE userId = :userId AND organizationId = :organizationId")
  Mono<Integer> deleteByUserIdAndOrganizationId(
      @Param("userId") long userId, @Param("organizationId") long organizationId);

  @Query("SELECT * FROM LdapConfiguration WHERE organizationId = :organizationId")
  Mono<LdapConfigurationDao> getLdapById(@Param("organizationId") long organizationId);
}

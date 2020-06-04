package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

public interface OrganizationRoleRepository
    extends ReactiveCrudRepository<OrganizationRoleDao, Long> {

  @Modifying
  @Query(
      "DELETE FROM `OrganizationRole` WHERE userId = :userId AND organizationId = :organizationId")
  Mono<Integer> deleteByOrganizationIdAndUserId(
      @Param("organizationId") final long organizationId, @Param("userId") final long userId);

  Mono<OrganizationRoleDao> findByOrganizationIdAndUserId(
      @Param("organizationId") final long organizationId, @Param("userId") final long userId);
}

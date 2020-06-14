package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface LdapConfigurationRepository
    extends ReactiveCrudRepository<LdapConfigurationDao, Long> {

  Mono<LdapConfigurationDao> findByOrganizationId(
      @Param("organizationId") final long organizationId);

  @Query(
      "UPDATE LdapConfiguration SET organizationId = :organizationId, url = :url, baseDn = :baseDn, bindDn = :bindDn, bindPassword = :bindPassword WHERE id = :id")
  Mono<LdapConfigurationDao> update(
      @Param("id") final long id,
      @Param("organizationId") final long organizationId,
      @Param("url") final String url,
      @Param("baseDn") final String baseDn,
      @Param("bindDn") final String bindDn,
      @Param("bindPassword") final String bindPassword);
}

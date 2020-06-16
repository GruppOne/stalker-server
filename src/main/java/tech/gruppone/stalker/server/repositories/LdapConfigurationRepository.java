package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface LdapConfigurationRepository
    extends ReactiveCrudRepository<LdapConfigurationDao, Long> {

  Mono<LdapConfigurationDao> findByOrganizationId(
      @Param("organizationId") final long organizationId);
}

package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface LdapConfigurationRepository
    extends ReactiveCrudRepository<LdapConfigurationDao, Long> {

  // The save method has been overridden because this method invoked by save(organizationDataDto)
  // produces only UPDATE query, but not INSERT query
  @Query(
      "INSERT INTO LdapConfiguration (organizationId, url, baseDn, bindDn, bindPassword) VALUES(:organizationId, :url, :baseDn, :bindDn, :bindPassword)")
  Mono<LdapConfigurationDao> save(
      Long organizationId, String url, String baseDn, String bindDn, String bindPassword);
}

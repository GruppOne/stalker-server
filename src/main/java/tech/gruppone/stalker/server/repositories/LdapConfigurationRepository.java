package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;

public interface LdapConfigurationRepository extends ReactiveCrudRepository<LdapConfigurationDao, Long> {}

package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tech.gruppone.stalker.server.model.db.OrganizationDao;

public interface LdapConfigurationRepository
    extends ReactiveCrudRepository<OrganizationDao, Long> {}

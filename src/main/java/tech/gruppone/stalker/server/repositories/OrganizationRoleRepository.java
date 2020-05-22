package tech.gruppone.stalker.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

public interface OrganizationRoleRepository extends ReactiveCrudRepository<OrganizationRole, Long> {

  // TODO check if this works without @query
  Mono<OrganizationRole> findByOrganizationIdAndUserId(
      final long organizationId, final long userId);
}

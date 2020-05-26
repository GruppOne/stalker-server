package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

public interface UserRoleRepository extends ReactiveCrudRepository<OrganizationRoleDao, Long> {

  @Query("SELECT * FROM OrganizationRole r, User u, Organization o WHERE r.userId = :userId AND r.organizationId = o.id AND r.userId = u.id")
  Flux<OrganizationRoleDao> findByUserId(final long userId);
}

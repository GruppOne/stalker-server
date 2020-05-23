package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

public interface OrganizationRoleRepository
    extends ReactiveCrudRepository<OrganizationRoleDao, Long> {

  @Query(
      "SELECT * FROM OrganizationRole r, Organization o, User u, AdministratorType a WHERE r.organizationId = o.id AND r.userId = u.id AND r.administratorType = a.id AND r.organizationId = :id")
  Flux<OrganizationRoleDao> findUsersRoles(long id);
}

package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.security.UserRoles;

public interface RoleRepository extends ReactiveCrudRepository<UserRoles, Long> {

  @Query("SELECT r.organizationId, a.role FROM OrganizationRole r, AdminType a, Users u WHERE u.id=r.userId AND a.name=r.name and u.email=:username")
  public Flux<UserRoles> findbyUser(String username);

}
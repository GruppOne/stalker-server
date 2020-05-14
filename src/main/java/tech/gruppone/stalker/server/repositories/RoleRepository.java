package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.UserRoleDto;

public interface RoleRepository extends ReactiveCrudRepository<UserRoleDto, Long> {

  @Query("SELECT r.organizationId, a.role FROM OrganizationRole r, AdministratorType a, User u WHERE u.id = r.userId AND a.id = r.administratorType and u.email = ':username'")
  public Flux<UserRoleDto> findUserRoles(@Param("username") String username);

}

package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.Organization;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdUsersConnectionsResponse;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationsResponse;
import tech.gruppone.stalker.server.model.api.responses.PostIdResponse;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization,Long> {

  @Query("SELECT id, name, description, createdDate, lastModifiedDate FROM Organizations")
  public Mono<GetOrganizationsResponse> findAllOrganizations();

  //TODO refactor this query: not valid if ldapConf isn't null; there aren't the places of an organization, as on the api specification
  @Query("SELECT id, name, description, ldapConf, createdDate, lastModifiedDate FROM Organizations o WHERE o.id = :id")
  public Mono<Organization> findById(Long id);

  @Modifying
  @Query("SELECT u.id, u.email, d.firstName, d.lastName, d.birthDate, d.createdDate, d.lastModifiedDate FROM Users u, Connections c, UserData d WHERE c.organizationId = :id AND c.userId = u.id AND u.id = d.userId")
  public Mono<GetOrganizationOrganizationIdUsersConnectionsResponse> findAllUsers(Long id);

  //TODO refactor this query: incomplete parameters
  @Modifying
  @Query("UPDATE Organizations o SET o.name = :name, o.description = :description WHERE o.id = :id")
  public Mono<Organization> updateOrganizationById(Long id, String name, String description);

  //TODO refactor this query
  @Modifying
  @Query("INSERT INTO Organizations (name, description) VALUES (:name, :description)")
  public Mono<PostIdResponse> create(String name, String description);

  @Modifying
  @Query("DELETE FROM Organizations o WHERE o.id = :id")
  public Mono<Organization> delete(Long id);

}

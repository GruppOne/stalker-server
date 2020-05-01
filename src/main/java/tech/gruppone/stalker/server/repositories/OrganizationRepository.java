package tech.gruppone.stalker.server.repositories;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.models.api.Organization;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization,Long> {

  @Query("SELECT * FROM Organizations")
  public Flux<Organization> findAll();

  @Query("SELECT * FROM Organizations o WHERE o.id = :id")
  public Mono<Organization> findById(Long id);

  @Modifying
  @Query("UPDATE Organizations o SET o.name = :name, o.description = :description WHERE o.id = :id")
  public Mono<Organization> updateOrganizationById(Long id, String name, String description);

  @Modifying
  @Query("INSERT INTO Organizations (name, description) VALUES (:name, :description)")
  public Mono<Organization> create(String name, String description);

  @Modifying
  @Query("DELETE FROM Organizations o WHERE o.id = :id")
  public Mono<Organization> delete(Long id);


  @Modifying
  @Query("SELECT u.email FROM Users u,Connections c WHERE c.organizationId = :id AND c.userId = u.id")
  public Flux<User> findAllUsers(Long id);

}

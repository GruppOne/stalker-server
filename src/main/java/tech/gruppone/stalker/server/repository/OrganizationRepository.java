package tech.gruppone.stalker.server.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;

public interface OrganizationRepository extends ReactiveCrudRepository<Organization,Long> {

  @Query("select * from Organizations")
  public Flux<Organization> findAll();


  @Query("select * from Organizations o where o.id = :id")
  public Mono<Organization> findById(Long id);

  @Modifying
  @Query("update Organizations o set o.name = :name, o.description = :description where o.id = :id")
  public Mono<Organization> update(Long id, String name, String description);

  @Modifying
  @Query("insert into Organizations (name, description) values (:name, :description)")
  public Mono<Organization> create(String name, String description);

  @Modifying
  @Query("delete from Organizations o where o.id = :id")
  public Mono<Organization> delete(Long id);


  @Modifying
  @Query("select u.email from Users u,Connections c where c.organizationId = :id and c.userId = u.id");
  public Flux<User> findAllusers(Long id);

}

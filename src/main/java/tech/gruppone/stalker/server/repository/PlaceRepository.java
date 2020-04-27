package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.Place;

public interface PlaceRepository extends ReactiveCrudRepository<Place,Long> {

  @Query("select id,organizationId,name,position from Place p where p.organizationId = :orgId")
  public Flux<Place> findAll(Long orgId);

  @Modifying
  @Query("insert into Place (name,organizationId) values (:name,:orgId)")
  public Flux<Place> create(String name,Long orgId);

  @Modifying
  @Query("update Place pla set pla.name = :name where pla.id = :id and pla.organizationId = :orgId")
  //@Query("update Place pla,PlaceData pd, Polygon pol set pla.name = :name, pd.address = :address, pd.city = :city, pd.zipcode = :zipcode, pd.state = :state, where o.id = :id")
  //public Flux<Place> update(Long orgId,Long id, String name, String address, String city, String zipcode, String state);
  public Flux<Place> update(String name,Long id, Long orgId);


  @Modifying
  @Query("delete from Place where organizationId = :orgId and id = :id")
  //@Query("update Place pla,PlaceData pd, Polygon pol set pla.name = :name, pd.address = :address, pd.city = :city, pd.zipcode = :zipcode, pd.state = :state, where o.id = :id")
  //public Flux<Place> update(Long orgId,Long id, String name, String address, String city, String zipcode, String state);
  public Flux<Place> delete(Long ordId, Long id);
}

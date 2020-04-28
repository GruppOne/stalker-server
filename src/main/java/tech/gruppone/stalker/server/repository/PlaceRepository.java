package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Place;

public interface PlaceRepository extends ReactiveCrudRepository<Place,Long> {

  @Query("select p.name,CONVERT(ST_AsText(p.position), CHAR) as position,pd.address,pd.city,pd.state,pd.zipcode from Place p, PlaceData pd where p.organizationId = :orgId and p.id = pd.id")
  public Flux<Place> findAll(Long orgId);


  @Query("select p.name,CONVERT(ST_AsText(p.position), CHAR) as position,pd.address,pd.city,pd.state,pd.zipcode from Place p, PlaceData pd where p.organizationId = :orgId and p.id = pd.id and p.id = :id")
  public Mono<Place> find(Long orgId,Long id);

  @Modifying
  @Query("insert into Place (name,position,organizationId) values (:name,ST_PolygonFromText(:positon),:orgId); SET @lastId := (select id from Place order by id DESC limit 1); insert into PlaceData (id,address,city,state,zipcode) values (@LastId,:address,:city,:state,:zipcode)")
  public Mono<Place> create(String name,String position, Long orgId,String address,String city,String state, String zipcode);

  @Modifying
  @Query("update Place pla,PlaceData pd set pla.name = :name, pd.address = :address, pd.city = :city, pd.state = :state, pd.zipcode = :zipcode, pla.position = ST_PolygonFromText(:positon) where pd.id = pla.id and pla.id = :id and pla.organizationId = :orgId")
  //@Query("update Place pla,PlaceData pd, Polygon pol set pla.name = :name, pd.address = :address, pd.city = :city, pd.zipcode = :zipcode, pd.state = :state, where o.id = :id")
  //public Flux<Place> update(Long orgId,Long id, String name, String address, String city, String zipcode, String state);
  public Mono<Place> update(String name,String address,String city,String state, String zipcode,String position, Long id, Long orgId);


  @Modifying
  @Query("delete from Place where organizationId = :orgId and id = :id")
  //@Query("update Place pla,PlaceData pd, Polygon pol set pla.name = :name, pd.address = :address, pd.city = :city, pd.zipcode = :zipcode, pd.state = :state, where o.id = :id")
  //public Flux<Place> update(Long orgId,Long id, String name, String address, String city, String zipcode, String state);
  public Mono<Place> delete(Long ordId, Long id);
}

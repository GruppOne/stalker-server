package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.PlaceDao;

// TODO reduce query verbosity
// TODO the inherited methods do not work. maybe should not extend ReactiveCrudRepository?
public interface PlaceRepository extends ReactiveCrudRepository<PlaceDao, Long> {

  @Query(
      "SELECT Place.id AS id, Place.organizationId AS organizationId, Place.name AS NAME, ST_ASGEOJSON(position) AS positionGeoJson, PlaceData.address, PlaceData.city, PlaceData.zipcode, PlaceData.state FROM Place, PlaceData WHERE Place.id = PlaceData.id AND Place.id = :id")
  Mono<PlaceDao> findById(final long id);

  @Query(
      "SELECT Place.id AS id, Place.organizationId AS organizationId, Place.name AS NAME, ST_ASGEOJSON(position) AS positionGeoJson, PlaceData.address, PlaceData.city, PlaceData.zipcode, PlaceData.state FROM Place, PlaceData WHERE Place.id = PlaceData.id AND organizationId = :organizationId")
  Flux<PlaceDao> findAllByOrganizationId(final long organizationId);

  // XXX EXTREMELY INEFFICIENT: does a separate query for each new place
  // this will cause problems because it does not know if the place had already been created. should
  // be fine after refactoring PlaceDao
  @Modifying
  @Query(
      "INSERT INTO `Place`(`organizationId`, `name`, `position`) VALUES (:organizationId, :name, ST_GeomFromGeoJSON(:rawPositionJson))")
  // handle errors by returning Mono<Integer>
  Mono<Void> create(final Long organizationId, final String name, final String rawPositionJson);
}

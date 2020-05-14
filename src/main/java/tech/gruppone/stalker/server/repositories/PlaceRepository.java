package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.PlaceDao;

// TODO reduce query verbosity
// TODO the inherited methods do not work. maybe should not extend ReactiveCrudRepository?
public interface PlaceRepository extends ReactiveCrudRepository<PlaceDao, Long> {

  @Query(
      "SELECT Place.id AS id, Place.organizationId AS organizationId, Place.name AS NAME, ST_ASGEOJSON(POSITION) AS positionGeoJson, PlaceData.address, PlaceData.city, PlaceData.zipcode, PlaceData.state FROM Place, PlaceData WHERE Place.id = PlaceData.id AND Place.id = :id")
  Mono<PlaceDao> findById(final long id);

  @Query(
      "SELECT Place.id AS id, Place.organizationId AS organizationId, Place.name AS NAME, ST_ASGEOJSON(POSITION) AS positionGeoJson, PlaceData.address, PlaceData.city, PlaceData.zipcode, PlaceData.state FROM Place, PlaceData WHERE Place.id = PlaceData.id AND organizationId = :organizationId")
  Flux<PlaceDao> findAllByOrganizationId(final long organizationId);
}

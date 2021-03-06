package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.PlacePositionDao;

// XXX remember that standard ReactiveCrudRepository methods do not work here
public interface PlacePositionRepository extends ReactiveCrudRepository<PlacePositionDao, Long> {

  @Query("SELECT id, ST_ASGEOJSON(position) AS positionGeoJson FROM PlacePosition WHERE id = :id")
  Mono<PlacePositionDao> findById(@Param("id") final Long id);

  // returns number of affected rows (should be 0 or 1)
  @Modifying
  @Query(
      "INSERT INTO `PlacePosition`(`id`, `position`) VALUES (:id, ST_GeomFromGeoJSON(:rawPositionJson))")
  Mono<Integer> save(
      @Param("id") final Long id, @Param("rawPositionJson") final String rawPositionJson);

  @Modifying
  @Query(
      "UPDATE PlacePosition SET `position` = ST_GeomFromGeoJSON(:rawPositionJson) WHERE `id` = :id")
  Mono<Integer> update(
      @Param("id") final Long id, @Param("rawPositionJson") final String rawPositionJson);
}

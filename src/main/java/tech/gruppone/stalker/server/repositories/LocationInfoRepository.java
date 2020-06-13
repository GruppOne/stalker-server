package tech.gruppone.stalker.server.repositories;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.eq;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.querybuilder.BuiltQuery.QueryBuilder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.InfluxDbConfiguration;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.db.LocationInfo;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
// TODO refactor so as to return monos and fluxes?
public class LocationInfoRepository {
  InfluxDB influxDB;
  InfluxDBMapper influxDBMapper;

  InfluxDbConfiguration influxDbConfiguration;

  private void save(final LocationInfo model, final String retentionPolicy) {
    final String database = influxDbConfiguration.getDatabase();

    // check if precision gets picked up correctly...
    final var point = Point.measurementByPOJO(LocationInfo.class).addFieldsFromPOJO(model).build();

    influxDB.write(database, retentionPolicy, point);
  }

  public void save(final LocationInfo model) {
    save(model, influxDbConfiguration.getDefaultRetentionPolicy());
  }

  public void saveWithInfiniteRp(final LocationInfo model) {
    save(model, influxDbConfiguration.getInfiniteRetentionPolicy());
  }

  public Mono<Boolean> findLastStatusByUserIdAndPlaceId(final String userId, final String placeId) {
    final String database = influxDbConfiguration.getDatabase();
    final String measurement = influxDbConfiguration.getMeasurement();
    final String columnName = "inside";

    final Query query =
        QueryBuilder.select(columnName)
            .from(database, measurement)
            .where(eq("user_id", userId))
            .and(eq("place_id", placeId))
            .orderBy(desc())
            .limit(1);

    final var queryResult = influxDB.query(query);
    final var series = queryResult.getResults().get(0).getSeries();

    // honestly whoever chose to return null instead of an empty list deserves to rot in hell
    if (series == null) {
      log.info("user {} had no status in place {}", userId, placeId);
      return Mono.empty();
    }

    final var firstSeries = series.get(0);
    final var columnIndex = firstSeries.getColumns().indexOf(columnName);
    final var value = (Boolean) firstSeries.getValues().get(0).get(columnIndex);

    return Mono.just(value);
  }

  public List<LocationInfo> findByOrganizationId(long organizationId) {

    throw new NotImplementedException();
  }
}

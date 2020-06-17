package tech.gruppone.stalker.server.repositories;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.eq;

import java.sql.Timestamp;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.querybuilder.BuiltQuery.QueryBuilder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import tech.gruppone.stalker.server.configuration.InfluxDbConfiguration;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto.PlaceHistoryDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class LocationInfoRepository {
  InfluxDB influxDB;
  InfluxDBMapper influxDBMapper;

  InfluxDbConfiguration influxDbConfiguration;

  // this is sub-optimal
  private static final String USER_ID_TAG_NAME = "user_id";
  private static final String ORGANIZATION_ID_TAG_NAME = "organization_id";
  private static final String PLACE_ID_TAG_NAME = "place_id";
  private static final String INSIDE_FIELD_NAME = "inside";

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

    final Query query =
        QueryBuilder.select(INSIDE_FIELD_NAME)
            .from(database, measurement)
            .where(eq(USER_ID_TAG_NAME, userId))
            .and(eq(PLACE_ID_TAG_NAME, placeId))
            .orderBy(desc())
            .limit(1);

    final var queryResult = influxDB.query(query);
    final List<Series> series = queryResult.getResults().get(0).getSeries();

    // honestly whoever chose to return null instead of an empty list deserves to rot in hell
    if (series == null) {
      log.info("user {} had no status in place {}", userId, placeId);
      return Mono.empty();
    }

    final var firstSeries = series.get(0);
    final var columnIndex = firstSeries.getColumns().indexOf(INSIDE_FIELD_NAME);
    final var value = (Boolean) firstSeries.getValues().get(0).get(columnIndex);

    return Mono.just(value);
  }

  // flux of tuples with place_id and number of users inside
  public Flux<Tuple2<Long, Integer>> findByOrganizationIdGroupByPlaceId(final long organizationId) {

    final String database = influxDbConfiguration.getDatabase();
    final String measurement = influxDbConfiguration.getMeasurement();
    final String infiniteRetentionPolicy = influxDbConfiguration.getInfiniteRetentionPolicy();
    final String fullyQualifiedFrom =
        String.format("\"%s\".\"%s\".\"%s\"", database, infiniteRetentionPolicy, measurement);

    // we should only query for today's values
    final Query query =
        QueryBuilder.select()
            .column(INSIDE_FIELD_NAME)
            .from(database, fullyQualifiedFrom)
            .where(eq(ORGANIZATION_ID_TAG_NAME, String.valueOf(organizationId)))
            // .and(QueryBuilder.gte("time","2015-08-18T00:00:00Z"))
            .groupBy(PLACE_ID_TAG_NAME, USER_ID_TAG_NAME)
            .orderBy(desc())
            .limit(1);

    // should refactor this with influxDBMapper
    final var series = influxDB.query(query).getResults().get(0).getSeries();

    if (series == null) {
      log.info("users/inside query returned no results for organization {}", organizationId);
      return Flux.empty();
    }

    return countUsersInsidePlaces(series);
  }

  private Flux<Tuple2<Long, Integer>> countUsersInsidePlaces(final List<Series> series) {

    return Flux.fromIterable(series)
        .map(
            thisSeries -> {
              final Long placeId = Long.valueOf(thisSeries.getTags().get(PLACE_ID_TAG_NAME));
              // careful: column index of "inside" field is hardcoded
              final Integer countedInside = (boolean) thisSeries.getValues().get(0).get(1) ? 1 : 0;

              return Tuples.of(placeId, countedInside);
            })
        // group all the series with the same place_id together
        .groupBy(Tuple2::getT1)
        .flatMap(
            placeCounter ->
                // suboptimal; instantiates a new tuple at each step...
                placeCounter.reduce(
                    (accumulator, next) ->
                        Tuples.of(placeCounter.key(), accumulator.getT2() + next.getT2())));
  }

  public Flux<PlaceHistoryDto> findHistoryByOrganizationIdAndUserId(
      final Long organizationId, final Long userId) {

    final String database = influxDbConfiguration.getDatabase();
    final String measurement = influxDbConfiguration.getMeasurement();
    final String infiniteRetentionPolicy = influxDbConfiguration.getInfiniteRetentionPolicy();
    final String fullyQualifiedFrom =
        String.format("\"%s\".\"%s\".\"%s\"", database, infiniteRetentionPolicy, measurement);

    final Query query =
        QueryBuilder.select()
            .from(database, fullyQualifiedFrom)
            .where(eq(ORGANIZATION_ID_TAG_NAME, String.valueOf(organizationId)))
            .and(eq(USER_ID_TAG_NAME, String.valueOf(userId)))
            .orderBy(desc());

    final var result = influxDBMapper.query(query, LocationInfo.class);

    return Flux.fromIterable(result)
        .map(
            locationInfo -> {
              final Timestamp timestamp = Timestamp.from(locationInfo.getTime());
              final Long placeId = Long.valueOf(locationInfo.getPlaceId());
              final Boolean inside = locationInfo.getInside();

              return PlaceHistoryDto.builder()
                  .timestamp(timestamp)
                  .placeId(placeId)
                  .inside(inside)
                  .build();
            });
  }
}

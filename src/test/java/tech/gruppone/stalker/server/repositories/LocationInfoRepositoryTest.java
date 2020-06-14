package tech.gruppone.stalker.server.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBMapper;
import org.influxdb.querybuilder.BuiltQuery.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;
import tech.gruppone.stalker.server.configuration.InfluxDbConfiguration;
import tech.gruppone.stalker.server.model.db.LocationInfo;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = LocationInfoRepository.class)
@Import(InfluxDbConfiguration.class)
class LocationInfoRepositoryTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(1577840461);

  @MockBean InfluxDB influxDB;
  @MockBean InfluxDBMapper influxDBMapper;

  @Autowired LocationInfoRepository locationInfoRepository;

  final String userId = "1";
  final String placeId = "2";
  final String organizationId = "3";

  final LocationInfo model =
      LocationInfo.builder()
          .time(INSTANT)
          .userId(userId)
          .placeId(placeId)
          .organizationId(organizationId)
          .anonymous(false)
          .inside(true)
          .build();

  private final String expectedLineProtocol =
      "access_log,organization_id=3,place_id=2,user_id=1 anonymous=false,inside=true 1577840461000000000";

  @Test
  void testSave() {
    final String retentionPolicy = "default";

    final ArgumentCaptor<Point> captor = ArgumentCaptor.forClass(Point.class);

    locationInfoRepository.save(model);

    verify(influxDB).write(eq("stalker-tsdb"), eq(retentionPolicy), captor.capture());

    assertThat(captor.getValue().lineProtocol()).isEqualTo(expectedLineProtocol);
  }

  @Test
  void testSaveInfinite() {
    final String retentionPolicy = "infinite";

    final ArgumentCaptor<Point> captor = ArgumentCaptor.forClass(Point.class);

    locationInfoRepository.saveWithInfiniteRp(model);

    verify(influxDB).write(eq("stalker-tsdb"), eq(retentionPolicy), captor.capture());

    assertThat(captor.getValue().lineProtocol()).isEqualTo(expectedLineProtocol);
  }

  @Test
  void testFindLastStatusByUserIdAndPlaceId() {
    final var lastStatus = true;

    final Query query =
        QueryBuilder.select("inside")
            .from("stalker-tsdb", "access_log")
            .where(QueryBuilder.eq("user_id", userId))
            .and(QueryBuilder.eq("place_id", placeId))
            .orderBy(QueryBuilder.desc())
            .limit(1);

    final var series = new QueryResult.Series();
    final var result = new QueryResult.Result();
    final QueryResult queryResult = new QueryResult();

    series.setColumns(List.of("inside"));
    series.setValues(List.of(List.of(lastStatus)));
    result.setSeries(List.of(series));
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findLastStatusByUserIdAndPlaceId(userId, placeId);

    StepVerifier.create(sut).expectNext(lastStatus).verifyComplete();

    verify(influxDB).query(query);
  }

  @Test
  void testFindLastStatusByUserIdAndPlaceIdWhenNoPreviousStatus() {
    final Query query =
        QueryBuilder.select("inside")
            .from("stalker-tsdb", "access_log")
            .where(QueryBuilder.eq("user_id", userId))
            .and(QueryBuilder.eq("place_id", placeId))
            .orderBy(QueryBuilder.desc())
            .limit(1);

    final var result = new QueryResult.Result();
    final QueryResult queryResult = new QueryResult();

    result.setSeries(null);
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findLastStatusByUserIdAndPlaceId(userId, placeId);

    StepVerifier.create(sut).verifyComplete();

    verify(influxDB).query(query);
  }

  @Test
  void testFindByOrganizationIdGroupByPlaceId() {
    final long organizationIdLong = 1L;
    final long placeId1 = 1L;
    final long placeId2 = 2L;
    final long userId1 = 11L;
    final long userId2 = 12L;

    final boolean lastStatus1 = false;
    final boolean lastStatus2 = true;

    final Query query =
        QueryBuilder.select()
            .column("inside")
            .from("stalker-tsdb", "\"stalker-tsdb\".\"infinite\".\"access_log\"")
            .where(QueryBuilder.eq("organization_id", String.valueOf(organizationIdLong)))
            .groupBy("place_id", "user_id")
            .orderBy(QueryBuilder.desc())
            .limit(1);

    final var series1 = new QueryResult.Series();
    final var series2 = new QueryResult.Series();
    final var result = new QueryResult.Result();
    final QueryResult queryResult = new QueryResult();

    series1.setColumns(List.of("time", "inside"));
    series1.setTags(
        Map.of("place_id", String.valueOf(placeId1), "user_id", String.valueOf(userId1)));
    series1.setValues(List.of(List.of("unused", lastStatus1)));

    series2.setColumns(List.of("inside"));
    series2.setTags(
        Map.of("place_id", String.valueOf(placeId2), "user_id", String.valueOf(userId2)));
    series2.setValues(List.of(List.of("unused", lastStatus2)));

    result.setSeries(List.of(series1, series2));
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationIdLong);

    StepVerifier.create(sut)
        .expectNext(Tuples.of(placeId1, 0))
        .expectNext(Tuples.of(placeId2, 1))
        .verifyComplete();

    verify(influxDB).query(query);
  }

  @Test
  void testFindByOrganizationIdGroupByPlaceIdWhenNoSeries() {
    final long organizationIdLong = 1L;

    final Query query =
        QueryBuilder.select()
            .column("inside")
            .from("stalker-tsdb", "\"stalker-tsdb\".\"infinite\".\"access_log\"")
            .where(QueryBuilder.eq("organization_id", String.valueOf(organizationIdLong)))
            .groupBy("place_id", "user_id")
            .orderBy(QueryBuilder.desc())
            .limit(1);

    final var result = new QueryResult.Result();
    final QueryResult queryResult = new QueryResult();

    result.setSeries(null);
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationIdLong);

    StepVerifier.create(sut).verifyComplete();

    verify(influxDB).query(query);
  }
}

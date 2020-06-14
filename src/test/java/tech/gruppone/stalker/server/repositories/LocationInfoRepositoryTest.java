package tech.gruppone.stalker.server.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.eq;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBMapper;
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
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto.PlaceHistoryDto;
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
        select("inside")
            .from("stalker-tsdb", "access_log")
            .where(eq("user_id", userId))
            .and(eq("place_id", placeId))
            .orderBy(desc())
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
        select("inside")
            .from("stalker-tsdb", "access_log")
            .where(eq("user_id", userId))
            .and(eq("place_id", placeId))
            .orderBy(desc())
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

    final boolean lastStatus1 = true;
    final boolean lastStatus2 = true;

    final Query query =
        select()
            .column("inside")
            .from("stalker-tsdb", "\"stalker-tsdb\".\"infinite\".\"access_log\"")
            .where(eq("organization_id", String.valueOf(organizationIdLong)))
            .groupBy("place_id", "user_id")
            .orderBy(desc())
            .limit(1);

    final var series1 = new QueryResult.Series();
    final var series1bis = new QueryResult.Series();
    final var series2 = new QueryResult.Series();
    final var result = new QueryResult.Result();
    final QueryResult queryResult = new QueryResult();

    series1.setTags(
        Map.of("place_id", String.valueOf(placeId1), "user_id", String.valueOf(userId1)));
    series1.setValues(List.of(List.of("unused", lastStatus1)));

    series1bis.setTags(
        Map.of("place_id", String.valueOf(placeId1), "user_id", String.valueOf(userId2)));
    series1bis.setValues(List.of(List.of("unused", !lastStatus1)));

    series2.setTags(
        Map.of("place_id", String.valueOf(placeId2), "user_id", String.valueOf(userId2)));
    series2.setValues(List.of(List.of("unused", lastStatus2)));

    result.setSeries(List.of(series1, series1bis, series2));
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findByOrganizationIdGroupByPlaceId(organizationIdLong);

    StepVerifier.create(sut)
        .expectNext(Tuples.of(placeId1, 1))
        .expectNext(Tuples.of(placeId2, 1))
        .verifyComplete();

    verify(influxDB).query(query);
  }

  @Test
  void testFindByOrganizationIdGroupByPlaceIdWhenNoSeries() {
    final long organizationIdLong = 1L;

    final Query query =
        select()
            .column("inside")
            .from("stalker-tsdb", "\"stalker-tsdb\".\"infinite\".\"access_log\"")
            .where(eq("organization_id", String.valueOf(organizationIdLong)))
            .groupBy("place_id", "user_id")
            .orderBy(desc())
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

  @Test
  void testFindHistoryByOrganizationIdAndUserId() {

    final var organizationIdLong = 1L;
    final var userIdLong = 11L;

    final Query query =
        select()
            .from("stalker-tsdb", "\"stalker-tsdb\".\"infinite\".\"access_log\"")
            .where(eq("organization_id", String.valueOf(organizationIdLong)))
            .and(eq("user_id", String.valueOf(userIdLong)))
            .orderBy(desc());

    final var placeId1 = "11";
    final var placeId2 = "12";
    final var inside1 = true;
    final var inside2 = false;

    final LocationInfo model1 =
        LocationInfo.builder()
            .time(INSTANT)
            .userId(String.valueOf(userId))
            .placeId(placeId1)
            .organizationId(String.valueOf(organizationId))
            .anonymous(false)
            .inside(inside1)
            .build();

    final var model2 =
        LocationInfo.builder()
            .time(INSTANT)
            .userId(String.valueOf(userId))
            .placeId(placeId2)
            .organizationId(String.valueOf(organizationId))
            .anonymous(false)
            .inside(inside2)
            .build();

    when(influxDBMapper.query(query, LocationInfo.class)).thenReturn(List.of(model1, model2));

    final var expectedPlaceHistoryDto1 =
        PlaceHistoryDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .inside(inside1)
            .placeId(Long.valueOf(placeId1))
            .build();

    final var expectedPlaceHistoryDto2 =
        PlaceHistoryDto.builder()
            .timestamp(Timestamp.from(INSTANT))
            .inside(inside2)
            .placeId(Long.valueOf(placeId2))
            .build();

    final var sut = locationInfoRepository.findHistoryByOrganizationIdAndUserId(null, null);

    StepVerifier.create(sut)
        .expectNext(expectedPlaceHistoryDto1)
        .expectNext(expectedPlaceHistoryDto2)
        .verifyComplete();

    verify(influxDBMapper).query(query, LocationInfo.class);
  }
}

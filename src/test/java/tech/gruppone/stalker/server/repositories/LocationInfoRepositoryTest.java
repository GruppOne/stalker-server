package tech.gruppone.stalker.server.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
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
    series.setValues(List.of(List.of(true)));
    result.setSeries(List.of(series));
    queryResult.setResults(List.of(result));

    when(influxDB.query(query)).thenReturn(queryResult);

    final var sut = locationInfoRepository.findLastStatusByUserIdAndPlaceId(userId, placeId);

    StepVerifier.create(sut).expectNext(lastStatus).verifyComplete();

    verify(influxDB).query(query);
  }
}

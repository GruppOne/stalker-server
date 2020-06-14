package tech.gruppone.stalker.server.model.db;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;

@Builder
@AllArgsConstructor
// these are required by influxdb java client library
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Measurement(
    // must match the string in InfluxDbConfiguration.measurement
    name = "access_log",
    database = "stalker-tsdb",
    timeUnit = TimeUnit.MILLISECONDS)
// XXX careful: the nonnull are blatantly disregarded by influxDBMapper!
public class LocationInfo {

  @NonNull
  @TimeColumn
  @Column(name = "time")
  Instant time;

  @NonNull
  @Column(name = "user_id", tag = true)
  String userId;

  @NonNull
  @Column(name = "organization_id", tag = true)
  String organizationId;

  @NonNull
  @Column(name = "place_id", tag = true)
  String placeId;

  @NonNull
  @Column(name = "anonymous")
  Boolean anonymous;

  @NonNull
  @Column(name = "inside")
  Boolean inside;
}

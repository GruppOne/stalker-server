package tech.gruppone.stalker.server.model.db;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Builder
@Value
@Measurement(
    name = "complete_log",
    database = "stalker-tsdb",
    retentionPolicy = "default",
    timeUnit = TimeUnit.MILLISECONDS)
public class LocationInfo {

  @NonNull
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

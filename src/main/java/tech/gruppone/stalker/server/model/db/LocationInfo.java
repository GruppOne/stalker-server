package tech.gruppone.stalker.server.model.db;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Builder
@Value
// TODO can probably omit database and retentionPolicy
// @Measurement(name = "access_log", timeUnit = TimeUnit.MILLISECONDS)
@Measurement(
    name = "access_log",
    database = "stalker-tsdb",
    retentionPolicy = "stalker-tsdb-retention-policy",
    timeUnit = TimeUnit.MILLISECONDS)
public class LocationInfo {

  @NonNull
  @Column(name = "time")
  Instant time;

  @NonNull
  @Column(name = "user_id", tag = true)
  String userId;

  @With
  @NonNull
  @Column(name = "place_id", tag = true)
  String placeId;

  @With
  @NonNull
  @Column(name = "organization_id", tag = true)
  String organizationId;

  @NonNull
  @Column(name = "anonymous")
  Boolean anonymous;

  @NonNull
  @Column(name = "inside")
  Boolean inside;
}

package tech.gruppone.stalker.server.repository;

import org.influxdb.impl.InfluxDBMapper;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.db.LocationInfo;

@Value
@NonFinal
@Repository
public class LocationInfoRepository {
  @NonNull
  InfluxDBMapper influxDBMapper;

  public Flux<LocationInfo> saveAllLocationInfo(Flux<LocationInfo> allLocationInfo) {
    return allLocationInfo.doOnNext(influxDBMapper::save);
  }
}

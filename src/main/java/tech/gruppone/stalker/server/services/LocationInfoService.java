package tech.gruppone.stalker.server.services;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import lombok.Value;
import lombok.experimental.NonFinal;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.MultiLocationInfo;
import tech.gruppone.stalker.server.model.db.LocationInfo;

@Value
@NonFinal
@Service
public class LocationInfoService {

  public Flux<LocationInfo> locationInfoSupplier(final MultiLocationInfo multiLocationInfo) {

    final LocationInfo.LocationInfoBuilder locationInfoBuilder = LocationInfo.builder()
        .time(multiLocationInfo.getTimestamp().toInstant()).userId(String.valueOf(multiLocationInfo.getUserId()))
        .anonymous(multiLocationInfo.getAnonymous()).inside(multiLocationInfo.getInside());

    final Function<String, LocationInfo> buildLocationInfo = placeId -> locationInfoBuilder.placeId(placeId).build();

    return Flux.fromIterable(multiLocationInfo.getPlaceIds()).map(String::valueOf).map(buildLocationInfo).log();
  }
}

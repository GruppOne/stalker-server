package tech.gruppone.stalker.server.services;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.MultiLocationInfo;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;

@Value
@NonFinal
@Service
public class LocationInfoService {

  @NonNull LocationInfoRepository locationInfoRepository;

  public Flux<LocationInfo> save(final MultiLocationInfo multiLocationInfo) {

    final LocationInfo.LocationInfoBuilder locationInfoBuilder =
        LocationInfo.builder()
            .time(multiLocationInfo.getTimestamp().toInstant())
            .userId(String.valueOf(multiLocationInfo.getUserId()))
            .anonymous(multiLocationInfo.getAnonymous())
            .inside(multiLocationInfo.getInside());

    final Function<String, LocationInfo> buildLocationInfo =
        placeId -> locationInfoBuilder.placeId(placeId).build();

    final Flux<LocationInfo> allLocationInfo =
        Flux.fromIterable(multiLocationInfo.getPlaceIds())
            .map(String::valueOf)
            .map(buildLocationInfo)
            .log();

    return locationInfoRepository.saveAllLocationInfo(allLocationInfo);
  }
}

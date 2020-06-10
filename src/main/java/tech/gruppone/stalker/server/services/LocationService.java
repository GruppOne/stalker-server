package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LocationService {

  InfluxDBMapper influxDBMapper;
  PlaceRepository placeRepository;

  public Mono<Void> saveMulti(final MultiLocationInfoDto multiLocationInfo) {

    // reusable builder. is this reasonably thread safe?
    final var locationInfoBuilder =
        LocationInfo.builder()
            .time(multiLocationInfo.getTimestamp().toInstant())
            .userId(multiLocationInfo.getUserId())
            .anonymous(multiLocationInfo.isAnonymous())
            .inside(multiLocationInfo.getInside());

    return placeRepository
        .findAllById(multiLocationInfo.getPlaceIds())
        .map(
            place -> {
              log.info("found place {}", place.getId());
              final String placeId = String.valueOf(place.getId());
              final String organizationId = String.valueOf(place.getOrganizationId());

              return locationInfoBuilder.placeId(placeId).organizationId(organizationId).build();
            })
        .doOnNext(
            locationInfo -> {
              log.info("saving row {}", locationInfo);
              influxDBMapper.save(locationInfo);
            })
        .then();
  }
}

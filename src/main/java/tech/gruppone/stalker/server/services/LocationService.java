package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LocationService {

  InfluxDBMapper influxDBMapper;
  PlaceRepository placeRepository;

  public Mono<Void> save(final MultiLocationInfoDto multiLocationInfo) {

    final var locationInfoWithBadId =
        LocationInfo.builder()
            .time(multiLocationInfo.getTimestamp().toInstant())
            .userId(String.valueOf(multiLocationInfo.getUserId()))
            .placeId("FAKE-ID")
            .anonymous(multiLocationInfo.getAnonymous())
            .inside(multiLocationInfo.getInside())
            .build();

    return placeRepository
        .findAllById(multiLocationInfo.getPlaceIds())
        .map(
            place -> {
              final String placeId = String.valueOf(place.getId());
              final String organizationId = String.valueOf(place.getOrganizationId());

              return locationInfoWithBadId.withPlaceId(placeId).withOrganizationId(organizationId);
            })
        .doOnNext(influxDBMapper::save)
        .then();
  }
}

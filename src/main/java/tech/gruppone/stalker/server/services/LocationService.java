package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.model.api.UsersInsideOrganizationDto;
import tech.gruppone.stalker.server.model.db.LocationInfo;
import tech.gruppone.stalker.server.repositories.MeasurementsRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LocationService {

  MeasurementsRepository measurementsRepository;

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
              final String placeId = String.valueOf(place.getId());
              final String organizationId = String.valueOf(place.getOrganizationId());

              return locationInfoBuilder.placeId(placeId).organizationId(organizationId).build();
            })
        // XXX the controller will respond with 201 CREATED even if there are errors here
        .doOnNext(this::conditionallyUpdateAccessLog)
        .doOnNext(
            locationInfo -> {
              log.info("saving row with default retention policy");
              measurementsRepository.save(locationInfo);
            })
        .then();
  }

  private void conditionallyUpdateAccessLog(final LocationInfo currentlocationInfo) {
    final var placeId = currentlocationInfo.getPlaceId();
    final var currentInside = currentlocationInfo.getInside().booleanValue();

    measurementsRepository
        .findLastStatusByUserIdAndPlaceId(currentlocationInfo.getUserId(), placeId)
        .filter(lastInside -> lastInside.booleanValue() != currentInside)
        .subscribe(
            lastInside -> {
              log.info(
                  "saving row with infinite retention policy. last 'inside' value was {}",
                  lastInside);
              measurementsRepository.saveInfinite(currentlocationInfo);
            });
  }

  public Mono<UsersInsideOrganizationDto> countUsersCurrentlyInsideOrganizationById(
      final long organizationId) {

    return Mono.error(NotImplementedException::new);
  }
}

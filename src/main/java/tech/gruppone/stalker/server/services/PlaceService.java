package tech.gruppone.stalker.server.services;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceService {
  Clock clock;

  OrganizationRepository organizationRepository;
  PlaceRepository placeRepository;

  PlacePositionService placePositionService;

  private PlaceDto fromTuple(final Tuple2<PlaceDao, List<GeographicalPoint>> tuple) {
    final PlaceDao placeDao = tuple.getT1();
    final List<GeographicalPoint> geographicalPoints = tuple.getT2();

    final var id = placeDao.getId();
    final var name = placeDao.getName();
    final var color = placeDao.getColor();
    final var maxConcurrentUsers = placeDao.getMaxConcurrentUsers();

    final var address = placeDao.getAddress();
    final var city = placeDao.getCity();
    final var zipcode = placeDao.getZipcode();
    final var state = placeDao.getState();

    final var placeInfo =
        PlaceInfo.builder().address(address).city(city).zipcode(zipcode).state(state).build();

    final var placeDataDto =
        PlaceDataDto.builder()
            .name(name)
            .color(color)
            .maxConcurrentUsers(maxConcurrentUsers)
            .polygon(geographicalPoints)
            .placeInfo(placeInfo)
            .build();

    return new PlaceDto(id, placeDataDto);
  }

  public Mono<PlaceDto> findById(final long id, final long organizationId) {
    return placeRepository
        .findById(id)
        .filter(place -> place.getOrganizationId().equals(organizationId))
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .zipWith(placePositionService.findGeographicalPointsByPlaceId(id))
        .map(this::fromTuple);
  }

  public Flux<PlaceDto> findAllByOrganizationId(final long id) {
    return placeRepository
        .findAllByOrganizationId(id)
        // XXX not optimal; does 1 db call per place.
        .flatMap(
            place -> {
              final var geographicalPoints =
                  placePositionService.findGeographicalPointsByPlaceId(place.getId());

              return Mono.just(place).zipWith(geographicalPoints);
            })
        .map(this::fromTuple);
  }

  private PlaceDao fromDataDto(final long organizationId, final PlaceDataDto placeDataDto) {

    return PlaceDao.builder()
        .organizationId(organizationId)
        .name(placeDataDto.getName())
        .color(placeDataDto.getColor())
        .maxConcurrentUsers(placeDataDto.getMaxConcurrentUsers())
        .address(placeDataDto.getPlaceInfo().getAddress())
        .city(placeDataDto.getPlaceInfo().getCity())
        .zipcode(placeDataDto.getPlaceInfo().getZipcode())
        .state(placeDataDto.getPlaceInfo().getState())
        .build();
  }

  public Mono<Long> save(final long organizationId, final PlaceDataDto placeDataDto) {

    final var placeDao = fromDataDto(organizationId, placeDataDto);
    final List<GeographicalPoint> polygon = placeDataDto.getPolygon();

    return placeRepository
        .save(placeDao)
        // save place position to separate table after place has been created successfully
        .doOnNext(
            createdPlace ->
                placePositionService
                    .save(createdPlace.getId(), polygon)
                    // update organization lastModified field after insert is successful
                    .then(updateOrganizationLastModified(organizationId)))
        .map(PlaceDao::getId);
  }

  public Mono<Void> updateById(
      final long placeId, final long organizationId, final PlaceDataDto placeDataDto) {

    final var placeDao = fromDataDto(organizationId, placeDataDto).withId(placeId);
    final var polygon = placeDataDto.getPolygon();

    return placeRepository
        .save(placeDao)
        // save place position even if not modified
        .then(placePositionService.save(placeId, polygon))
        .then(updateOrganizationLastModified(organizationId));
  }

  // not sure this is the correct way to handle this
  private Mono<Void> updateOrganizationLastModified(final Long organizationId) {
    return organizationRepository
        .findById(organizationId)
        .map(organization -> organization.withLastModifiedDate(LocalDateTime.now(clock)))
        .flatMap(organizationRepository::save)
        .then();
  }
}

package tech.gruppone.stalker.server.services;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.PlaceInfo;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PlaceService {
  PlaceRepository placeRepository;

  PlacePositionService placePositionService;

  public Flux<PlaceDao> saveAll(final Flux<PlaceDataDto> placeDataDtos, final long organizationId) {
    log.info("saving some places for organization {}", organizationId);

    var placeDaos = placeDataDtos.flatMap(placeDataDto -> save(placeDataDto, organizationId));

    placeDaos.subscribe(log::info);
    return placeDaos;
  }

  public Mono<PlaceDao> save(final PlaceDataDto placeDataDto, final long organizationId) {
    final String name = placeDataDto.getName();
    final String address = placeDataDto.getPlaceInfo().getAddress();
    final String city = placeDataDto.getPlaceInfo().getCity();
    final String zipcode = placeDataDto.getPlaceInfo().getZipcode();
    final String state = placeDataDto.getPlaceInfo().getState();

    log.info("saving place {} for organization {}", name, organizationId);

    final PlaceDao placeDao =
        PlaceDao.builder()
            .organizationId(organizationId)
            .name(name)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    // save place position to separate table
    final List<GeographicalPoint> polygon = placeDataDto.getPolygon();

    return placeRepository
        .save(placeDao)
        .doOnNext(
            createdPlace -> placePositionService.savePlacePosition(createdPlace.getId(), polygon));
  }

  private Mono<PlaceDto> convertDaoToDto(final PlaceDao placeDao) {
    final long id = placeDao.getId();
    final String name = placeDao.getName();

    final Mono<List<GeographicalPoint>> geographicalPoints =
        placePositionService.findGeographicalPointsByPlaceId(id);

    final PlaceInfo.PlaceInfoBuilder placeInfoBuilder =
        PlaceInfo.builder()
            .address(placeDao.getAddress())
            .city(placeDao.getCity())
            .zipcode(placeDao.getZipcode())
            .state(placeDao.getState());

    return geographicalPoints.map(
        polygon -> {
          var placeInfo = placeInfoBuilder.build();
          var placeDataDto =
              PlaceDataDto.builder().name(name).polygon(polygon).placeInfo(placeInfo).build();

          return new PlaceDto(id, placeDataDto);
        });
  }

  public Mono<PlaceDto> findById(final long id) {
    return placeRepository.findById(id).flatMap(this::convertDaoToDto);
  }

  public Flux<PlaceDto> findAllByOrganizationId(final long id) {
    return placeRepository.findAllByOrganizationId(id).flatMap(this::convertDaoToDto);
  }
}

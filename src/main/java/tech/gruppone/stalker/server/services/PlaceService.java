package tech.gruppone.stalker.server.services;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class PlaceService {
  PlaceRepository placeRepository;

  PlacePositionService placePositionService;

  public Flux<PlaceDao> saveAll(final Flux<PlaceDataDto> placeDataDtos, final long organizationId) {
    return placeDataDtos.flatMap(placeDataDto -> save(placeDataDto, organizationId));
  }

  public Mono<PlaceDao> save(final PlaceDataDto placeDataDto, final long organizationId) {
    final String name = placeDataDto.getName();
    final String address = placeDataDto.getPlaceInfo().getAddress();
    final String city = placeDataDto.getPlaceInfo().getCity();
    final String zipcode = placeDataDto.getPlaceInfo().getZipcode();
    final String state = placeDataDto.getPlaceInfo().getState();

    final PlaceDao placeDao =
        PlaceDao.builder()
            .organizationId(organizationId)
            .name(name)
            .address(address)
            .city(city)
            .zipcode(zipcode)
            .state(state)
            .build();

    final Mono<PlaceDao> savedPlaceDao = placeRepository.save(placeDao);

    // save place position to separate table
    final List<GeographicalPoint> polygon = placeDataDto.getPolygon();

    final Mono<Integer> howManyPlacePositionCreated =
        savedPlaceDao
            .map(PlaceDao::getId)
            .flatMap(createdId -> placePositionService.savePlacePosition(createdId, polygon));

    howManyPlacePositionCreated.subscribe();

    return savedPlaceDao;
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

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

  public Mono<PlaceDto> findById(final long id) {
    return placeRepository.findById(id).map(this::convertDaoToDto);
  }

  public Flux<PlaceDto> findAllByOrganizationId(final long id) {
    return placeRepository.findAllByOrganizationId(id).map(this::convertDaoToDto);
  }

  PlaceSerializationService positionService;

  private PlaceDto convertDaoToDto(final PlaceDao placeDao) {
    final long id = placeDao.getId();

    final String name = placeDao.getName();

    final List<GeographicalPoint> polygon =
        positionService.convertRawPositionJson(placeDao.getRawPositionJson());

    final PlaceInfo placeInfo =
        PlaceInfo.builder()
            .address(placeDao.getAddress())
            .city(placeDao.getCity())
            .zipcode(placeDao.getZipcode())
            .state(placeDao.getState())
            .build();

    final var placeDataDto =
        PlaceDataDto.builder().name(name).polygon(polygon).placeInfo(placeInfo).build();

    return new PlaceDto(id, placeDataDto);
  }

  public Flux<Void> saveAll(final List<PlaceDto> placeDtos) {

    return Flux.fromStream(placeDtos.stream())
        .flatMap(
            placeDto -> {
              var organizationId = placeDto.getId();
              var name = placeDto.getData().getName();

              var polygon = placeDto.getData().getPolygon();
              var position = positionService.convertGeographicalPoints(polygon);

              return placeRepository.create(organizationId, name, position);
            });
  }
}

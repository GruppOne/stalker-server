package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto.OrganizationDataDtoBuilder;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrganizationService {
  OrganizationRepository organizationRepository;
  PlaceService placeService;

  public Flux<OrganizationDto> findAll() {
    // I have no idea why this works
    return organizationRepository
        .findAll()
        .map(OrganizationDao::getId)
        .log()
        .flatMap(this::findById);
  }

  public Mono<OrganizationDto> findById(final long id) {
    final Flux<PlaceDto> places = placeService.findAllByOrganizationId(id);
    final Mono<OrganizationDao> organizationDao = organizationRepository.findById(id);

    final OrganizationDataDtoBuilder builder = OrganizationDataDto.builder();
    places.subscribe(builder::place);

    return places
        .then(organizationDao)
        .map(
            orgDao ->
                builder
                    .name(orgDao.getName())
                    .description(orgDao.getDescription())
                    .isPrivate(orgDao.getIsPrivate())
                    .creationDateTime(Timestamp.valueOf(orgDao.getCreatedDate()))
                    .lastChangeDateTime(Timestamp.valueOf(orgDao.getLastModifiedDate()))
                    .build())
        .map(data -> new OrganizationDto(id, data));
  }

  public Mono<Long> save(OrganizationDataDto organizationDataDto) {

    // XXX careful: there can be multiple organizations with the same name!
    var name = organizationDataDto.getName();
    var description = organizationDataDto.getDescription();
    // TODO implement private orgs
    var places = organizationDataDto.getPlaces();

    var newOrganizationDao = OrganizationDao.builder().name(name).description(description).build();

    var createdId = organizationRepository.save(newOrganizationDao).map(OrganizationDao::getId);

    return placeService.saveAll(places).then(createdId);
  }
}

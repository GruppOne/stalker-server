package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
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
    // Flux<OrganizationDao> organizationDaos = organizationRepository.findAll();

    // var organizationDataBuilder = OrganizationDataDto.builder();

    //  Flux<OrganizationDto> organizationDtos = organizations.flatMap(organization -> {
    //   Flux<PlaceDao> places = placeRepository.findByOrganizationId(organization.getId());
    // });
    return Flux.error(NotImplementedException::new);
  }

  public Mono<OrganizationDto> findById(final long id) {
    final Mono<OrganizationDao> organizationDao = organizationRepository.findById(id);
    final Flux<PlaceDto> places = placeService.findAllByOrganizationId(id);

    final OrganizationDataDtoBuilder builder = OrganizationDataDto.builder();

    organizationDao.doOnNext(
        orgDao ->
            builder
                .name(orgDao.getName())
                .description(orgDao.getDescription())
                .isPrivate(orgDao.isPrivate())
                .creationDateTime(orgDao.getCreatedDate())
                .lastChangeDateTime(orgDao.getLastModifiedDate()));

    return Mono.error(NotImplementedException::new);
  }
}

package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto.OrganizationDataDtoBuilder;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;
import tech.gruppone.stalker.server.model.api.UserDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.UserDataRepository;
import tech.gruppone.stalker.server.repositories.UserRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Log4j2
public class OrganizationService {
  OrganizationRepository organizationRepository;
  PlaceService placeService;
  UserRepository userRepository;
  UserDataRepository userDataRepository;

  public Flux<OrganizationDto> findAll() {
    // I have no idea why this works
    return organizationRepository.findAll().map(OrganizationDao::getId).flatMap(this::findById);
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

    // TODO implement private orgs
    // XXX careful: there can be multiple organizations with the same name!
    String name = organizationDataDto.getName();
    String description = organizationDataDto.getDescription();
    // TODO we should not have to have an id for places not yet created
    Flux<PlaceDataDto> placeDataDtos =
        Flux.fromIterable(organizationDataDto.getPlaces()).map(PlaceDto::getData);

    OrganizationDao newOrganizationDao =
        OrganizationDao.builder().name(name).description(description).build();

    return organizationRepository
        .save(newOrganizationDao)
        .doOnNext(orgDao -> log.info("created organization {}", orgDao))
        .map(OrganizationDao::getId)
        .doOnNext(organizationId -> placeService.saveAll(placeDataDtos, organizationId));
  }

  public Flux<UserDto> findConnectedUsersByOrganizationId(Long organizationId) {

    return userRepository
        .findAllUsers(organizationId)
        .zipWith(userDataRepository.findAllUserData(organizationId))
        .map(
            result -> {
              var t1 = result.getT1();
              var t2 = result.getT2();
              return UserDto.builder()
                  .id(t1.getId())
                  .data(
                      UserDataDto.builder()
                          .email(t1.getEmail())
                          .firstName(t2.getFirstName())
                          .lastName(t2.getLastName())
                          .birthDate(t2.getBirthDate())
                          .creationDateTime(Timestamp.valueOf(t2.getCreatedDate()))
                          .build())
                  .build();
            });
  }
}

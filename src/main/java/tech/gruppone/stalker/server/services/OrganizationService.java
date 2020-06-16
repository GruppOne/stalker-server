package tech.gruppone.stalker.server.services;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.db.LdapConfigurationDao;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrganizationService {
  Clock clock;

  OrganizationRepository organizationRepository;
  PlaceRepository placeRepository;
  LdapConfigurationRepository ldapConfigurationRepository;

  // TODO will need to merge organization with ldapconfiguration when implementing private orgs
  private OrganizationDto fromTuple(final Tuple2<OrganizationDao, List<Long>> tuple) {
    final var organization = tuple.getT1();
    final List<Long> placeIds = tuple.getT2();

    final Long id = organization.getId();
    final String name = organization.getName();
    final String description = organization.getDescription();
    final String organizationType = organization.getOrganizationType();
    final Timestamp creationDateTime = Timestamp.valueOf(organization.getCreatedDate());
    final Timestamp lastChangeDateTime = Timestamp.valueOf(organization.getLastModifiedDate());

    final var organizationData =
        OrganizationDataDto.builder()
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .creationDateTime(creationDateTime)
            .lastChangeDateTime(lastChangeDateTime)
            .build();

    return OrganizationDto.builder().id(id).data(organizationData).placeIds(placeIds).build();
  }

  public Flux<OrganizationDto> findAll() {
    final var organizationDaos = organizationRepository.findAll();
    final var placeDaos = placeRepository.findAll();

    return organizationDaos
        .flatMap(
            organizationDao -> {
              final Flux<Long> placeIds =
                  placeDaos
                      .filter(
                          placeDao -> placeDao.getOrganizationId().equals(organizationDao.getId()))
                      .map(PlaceDao::getId);

              return Mono.just(organizationDao).zipWith(placeIds.collectList());
            })
        .map(this::fromTuple);
  }

  public Mono<OrganizationDto> findById(final long id) {
    final Mono<OrganizationDao> organizationDao = organizationRepository.findById(id);
    final Flux<Long> placeIdsFlux =
        placeRepository.findAllByOrganizationId(id).map(PlaceDao::getId);

    return organizationDao.zipWith(placeIdsFlux.collectList()).map(this::fromTuple);
  }

  private OrganizationDao fromDataDtoWithoutDates(final OrganizationDataDto organizationDataDto) {

    final String name = organizationDataDto.getName();
    final String description = organizationDataDto.getDescription();
    final String organizationType = organizationDataDto.getOrganizationType();

    return OrganizationDao.builder()
        .name(name)
        .description(description)
        .organizationType(organizationType)
        .build();
  }

  private LdapConfigurationDao fromLdapConfigurationDto(
      final Long organizationId, final OrganizationDataDto.LdapConfiguration ldapConfiguration) {

    final String url = ldapConfiguration.getUrl();
    final String baseDn = ldapConfiguration.getBaseDn();
    final String bindRdn = ldapConfiguration.getBindRdn();
    final String bindPassword = ldapConfiguration.getBindPassword();

    return LdapConfigurationDao.builder()
        .organizationId(organizationId)
        .url(url)
        .baseDn(baseDn)
        .bindRdn(bindRdn)
        .bindPassword(bindPassword)
        .build();
  }

  public Mono<Long> save(final OrganizationDataDto organizationDataDto) {

    final OrganizationDao newOrganizationDao = fromDataDtoWithoutDates(organizationDataDto);

    return organizationRepository
        .save(newOrganizationDao)
        .map(OrganizationDao::getId)
        .flatMap(
            organization -> {
              if (newOrganizationDao.getOrganizationType().equals("private")) {
                final LdapConfigurationDao newLdapConfigurationDao =
                    fromLdapConfigurationDto(
                        organization, organizationDataDto.getLdapConfiguration());
                if (newLdapConfigurationDao == null) {
                  throw new BadRequestException();
                }
                return ldapConfigurationRepository
                    .save(newLdapConfigurationDao)
                    .map(LdapConfigurationDao::getOrganizationId);
              }
              return Mono.just(organization);
            });
  }

  public Mono<Void> updateById(final Long id, final OrganizationDataDto organizationDataDto) {

    final OrganizationDao updatedOrganization =
        fromDataDtoWithoutDates(organizationDataDto)
            .withId(id)
            .withCreatedDate(organizationDataDto.getCreationDateTime().toLocalDateTime())
            .withLastModifiedDate(LocalDateTime.now(clock));

    final LdapConfigurationDao updatedLdapConfiguration =
        fromLdapConfigurationDto(id, organizationDataDto.getLdapConfiguration());

    return organizationRepository
        .save(updatedOrganization)
        .flatMap(
            o -> {
              if (o.getOrganizationType().equals("private")) {
                if (organizationDataDto.getLdapConfiguration() == null) {
                  throw new BadRequestException();
                }

                return ldapConfigurationRepository
                    .findByOrganizationId(id)
                    .switchIfEmpty(Mono.error(NotFoundException::new))
                    .flatMap(
                        l ->
                            ldapConfigurationRepository.update(
                                l.getId(),
                                updatedLdapConfiguration.getOrganizationId(),
                                updatedLdapConfiguration.getUrl(),
                                updatedLdapConfiguration.getBaseDn(),
                                updatedLdapConfiguration.getBindRdn(),
                                updatedLdapConfiguration.getBindPassword()))
                    .then();
              } else {
                return Mono.empty();
              }
            });
  }
}

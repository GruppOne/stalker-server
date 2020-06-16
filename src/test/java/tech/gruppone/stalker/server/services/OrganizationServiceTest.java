package tech.gruppone.stalker.server.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.model.api.OrganizationDataDto;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.model.db.OrganizationDao;
import tech.gruppone.stalker.server.model.db.PlaceDao;
import tech.gruppone.stalker.server.repositories.LdapConfigurationRepository;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = OrganizationService.class)
class OrganizationServiceTest {

  @MockBean OrganizationRepository organizationRepository;
  @MockBean PlaceRepository placeRepository;
  @MockBean LdapConfigurationRepository ldapConfigurationRepository;

  @Autowired OrganizationService organizationService;

  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  // set some common variables
  final long organizationId = 1L;
  final String name = "name";
  final String description = "description";
  final String organizationType = "organizationType";

  // set some common place variables
  final Long placeId = 11L;
  final String placeName = "placeName";
  final String color = "color";
  final String address = "address";
  final String city = "city";
  final String zipcode = "zipcode";
  final String state = "state";
  final PlaceDao place =
      PlaceDao.builder()
          .id(placeId)
          .organizationId(organizationId)
          .name(placeName)
          .color(color)
          .maxConcurrentUsers(111)
          .address(address)
          .city(city)
          .zipcode(zipcode)
          .state(state)
          .build();
  final List<Long> placeIds = List.of(placeId);

  @Test
  void testFindAll() {

    final var expectedOrganizationDataDto =
        OrganizationDataDto.builder()
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .creationDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .lastChangeDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .build();

    final var expectedOrganizationDto =
        OrganizationDto.builder()
            .id(organizationId)
            .data(expectedOrganizationDataDto)
            .placeIds(placeIds)
            .build();

    final var organization =
        OrganizationDao.builder()
            .id(organizationId)
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(organizationRepository.findAll()).thenReturn(Flux.just(organization));
    when(placeRepository.findAll()).thenReturn(Flux.just(place));

    final var sut = organizationService.findAll();

    StepVerifier.create(sut).expectNext(expectedOrganizationDto).verifyComplete();

    verify(organizationRepository).findAll();
    verify(placeRepository).findAll();
  }

  @Test
  void testFindById() {

    final var placeIds = List.of(placeId);

    final var expectedOrganizationDataDto =
        OrganizationDataDto.builder()
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .creationDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .lastChangeDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .build();

    final var expectedOrganizationDto =
        OrganizationDto.builder()
            .id(organizationId)
            .data(expectedOrganizationDataDto)
            .placeIds(placeIds)
            .build();

    final var organization =
        OrganizationDao.builder()
            .id(organizationId)
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(LOCAL_DATETIME)
            .build();

    when(organizationRepository.findById(organizationId)).thenReturn(Mono.just(organization));
    when(placeRepository.findAllByOrganizationId(organizationId)).thenReturn(Flux.just(place));

    final var sut = organizationService.findById(organizationId);

    StepVerifier.create(sut).expectNext(expectedOrganizationDto).verifyComplete();

    verify(organizationRepository).findById(organizationId);
    verify(placeRepository).findAllByOrganizationId(organizationId);
  }

  @Test
  void testSave() {
    final var organizationDataDto =
        OrganizationDataDto.builder()
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .build();

    final var organizationDao =
        OrganizationDao.builder()
            .name(name)
            .description(description)
            .organizationType(organizationType)
            .build();

    final var newOrganizationId = 111L;

    when(organizationRepository.save(organizationDao))
        .thenReturn(
            Mono.just(
                organizationDao
                    .withId(newOrganizationId)
                    .withCreatedDate(LOCAL_DATETIME)
                    .withLastModifiedDate(LOCAL_DATETIME)));

    final var sut = organizationService.save(organizationDataDto);

    StepVerifier.create(sut).expectNext(newOrganizationId).verifyComplete();

    verify(organizationRepository).save(organizationDao);
  }

  @Test
  void testUpdateById() {
    final var newName = "newName";
    final var newDescription = "newDescription";

    final var organizationDataDto =
        OrganizationDataDto.builder()
            .name(newName)
            .description(newDescription)
            .organizationType(organizationType)
            .creationDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .lastChangeDateTime(Timestamp.valueOf(LOCAL_DATETIME))
            .build();

    final var updatedOrganization =
        OrganizationDao.builder()
            .id(organizationId)
            .name(newName)
            .description(newDescription)
            .organizationType(organizationType)
            .createdDate(LOCAL_DATETIME)
            .lastModifiedDate(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME)
            .build();

    when(organizationRepository.save(updatedOrganization))
        .thenReturn(Mono.just(updatedOrganization));

    final var sut = organizationService.updateById(organizationId, organizationDataDto);

    StepVerifier.create(sut).verifyComplete();

    verify(organizationRepository).save(updatedOrganization);
  }
}

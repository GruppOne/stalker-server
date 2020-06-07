package tech.gruppone.stalker.server.services;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.BadRequestException;
import tech.gruppone.stalker.server.exceptions.NotFoundException;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.api.RolesInOrganizationsDto;
import tech.gruppone.stalker.server.model.api.RolesInOrganizationsDto.RoleInOrganization;
import tech.gruppone.stalker.server.model.api.UsersWithRolesDto;
import tech.gruppone.stalker.server.model.api.UsersWithRolesDto.UserWithRole;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@Log4j2
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
  Clock clock;
  OrganizationRoleRepository organizationRoleRepository;

  public Mono<Void> create(
      final long organizationId, final long userId, final AdministratorType administratorType) {

    final OrganizationRoleDao organizationRoleDao =
        OrganizationRoleDao.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(administratorType)
            .build();

    return organizationRoleRepository
        .save(organizationRoleDao)
        .onErrorMap(
            DataIntegrityViolationException.class,
            error -> {
              log.error(error.getMessage());

              return new BadRequestException();
            })
        .then();
  }

  public Mono<Void> update(
      final long organizationId, final long userId, final AdministratorType modifiedRole) {

    return organizationRoleRepository
        .findByOrganizationIdAndUserId(organizationId, userId)
        .switchIfEmpty(Mono.error(NotFoundException::new))
        .flatMap(
            organizationRole -> {
              final var updatedOrganizationRole =
                  organizationRole
                      .withAdministratorType(modifiedRole)
                      .withCreatedDate(LocalDateTime.now(clock));

              return organizationRoleRepository.save(updatedOrganizationRole);
            })
        .then();
  }

  public Mono<UsersWithRolesDto> findUsersWithRolesByOrganizationId(final long organizationId) {

    return organizationRoleRepository
        .findAllByOrganizationId(organizationId)
        .map(
            organizationRole ->
                new UserWithRole(
                    organizationRole.getUserId(), organizationRole.getAdministratorType()))
        .collectList()
        .map(UsersWithRolesDto::new);
  }

  public Mono<RolesInOrganizationsDto> findRolesInOrganizationsByUserId(final long userId) {

    return organizationRoleRepository
        .findAllByUserId(userId)
        .map(
            organizationRole ->
                new RoleInOrganization(
                    organizationRole.getOrganizationId(), organizationRole.getAdministratorType()))
        .collectList()
        .map(RolesInOrganizationsDto::new);
  }
}

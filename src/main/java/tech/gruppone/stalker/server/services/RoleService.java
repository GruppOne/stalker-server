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
import tech.gruppone.stalker.server.model.db.OrganizationRole;
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

    final OrganizationRole organizationRole =
        OrganizationRole.builder()
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(administratorType)
            .build();

    return organizationRoleRepository
        .save(organizationRole)
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
    // TODO can we avoid the db round trip?

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
}

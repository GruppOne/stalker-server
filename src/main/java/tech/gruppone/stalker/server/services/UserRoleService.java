package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.UserRoleInOrganizationDto;
import tech.gruppone.stalker.server.repositories.UserRoleRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserRoleService {

  UserRoleRepository userRoleRepository;

  public Flux<UserRoleInOrganizationDto> findRoleByUserId(final long userId){

    return userRoleRepository
        .findByUserId(userId)
        .map(
          result ->
          UserRoleInOrganizationDto.builder()
          .organizationId(result.getOrganizationId())
          .role(result.getAdministratorType())
          .build()
        ).log();
  }
}

package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.api.OrganizationRoleDto;
import tech.gruppone.stalker.server.repositories.OrganizationRoleRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrganizationRoleService {

  OrganizationRoleRepository organizationRoleRepository;

  public Flux<OrganizationRoleDto> getUsersRoles(long organizationId) {
    return organizationRoleRepository
        .findUsersRoles(organizationId)
        .map(or -> new OrganizationRoleDto(or.getUserId(), or.getName()));
  }
}

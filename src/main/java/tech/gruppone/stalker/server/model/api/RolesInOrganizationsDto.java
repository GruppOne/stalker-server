package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.NonNull;
import lombok.Value;
import tech.gruppone.stalker.server.model.AdministratorType;

@Value
public class RolesInOrganizationsDto {

  @NonNull List<RoleInOrganization> rolesInOrganizations;

  @Value
  public static class RoleInOrganization {
    @NonNull Long organizationId;
    @NonNull AdministratorType role;
  }
}

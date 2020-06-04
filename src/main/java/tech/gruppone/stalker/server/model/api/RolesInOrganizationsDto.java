package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import tech.gruppone.stalker.server.model.AdministratorType;

@Builder
@Value
public class RolesInOrganizationsDto {

  @NonNull
  @Singular("roleInOrganization")
  List<RoleInOrganization> rolesInOrganizations;

  @Value
  public static class RoleInOrganization {
    @NonNull Long organizationId;
    @NonNull AdministratorType role;
  }
}

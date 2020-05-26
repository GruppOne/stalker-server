package tech.gruppone.stalker.server.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import tech.gruppone.stalker.server.model.AdministratorType;

@Builder
@Data
public class UserRoleInOrganizationDto {
  @NonNull Long organizationId;

  @JsonAlias({"newRole", "modifiedRole"})
  @NonNull
  AdministratorType role;
}

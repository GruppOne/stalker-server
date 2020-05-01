package tech.gruppone.stalker.server.models.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class OrganizationRole {

  @NonNull
  Long userId;

  @NonNull
  Long organizationId;

  @NonNull
  String role;

}

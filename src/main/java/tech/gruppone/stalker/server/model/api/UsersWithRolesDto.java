package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import tech.gruppone.stalker.server.model.AdministratorType;

@Builder
@Value
public class UsersWithRolesDto {

  @NonNull
  @Singular("userWithRole")
  List<UserWithRole> usersWithRoles;

  @Value
  public static class UserWithRole {
    @NonNull Long userId;
    @NonNull AdministratorType role;
  }
}

package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.NonNull;
import lombok.Value;
import tech.gruppone.stalker.server.model.AdministratorType;

@Value
public class UsersWithRolesDto {

  @NonNull List<UserWithRole> usersWithRoles;

  @Value
  public static class UserWithRole {
    @NonNull Long userId;
    @NonNull AdministratorType role;
  }
}

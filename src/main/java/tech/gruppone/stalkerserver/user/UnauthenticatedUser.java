package tech.gruppone.stalkerserver.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class UnauthenticatedUser {

  @NonNull String email;
  @NonNull String password;
}

package tech.gruppone.stalker.server.model.api;

import lombok.Data;
import lombok.NonNull;

@Data
public class UnauthenticatedUser {

  @NonNull String email;
  @NonNull String password;
}

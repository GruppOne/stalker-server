package tech.gruppone.stalker.server.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class UnauthenticatedUser {

  @NonNull String email;
  @NonNull String password;
}

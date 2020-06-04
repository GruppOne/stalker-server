package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class LoginDataDto {

  @NonNull String email;

  @NonNull String password;
}

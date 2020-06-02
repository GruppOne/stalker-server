package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserDto {
  long id;

  @NonNull UserDataDto data;
}

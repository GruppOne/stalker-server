package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserDataDto {
  @NonNull String email;

  @NonNull String firstName;

  @NonNull String lastName;

  @NonNull LocalDate birthDate;

  Timestamp creationDateTime;
}

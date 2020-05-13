package tech.gruppone.stalker.server.model.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class UserDataDto {
  @NonNull String email;

  @NonNull String firstName;

  @NonNull String lastName;

  @NonNull LocalDate birthDate;

  LocalDateTime creationDateTime;
}

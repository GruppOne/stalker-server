package tech.gruppone.stalker.server.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class UserDto {
  @Id long id;

  @NonNull UserDataDto userData;
}

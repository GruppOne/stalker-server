package tech.gruppone.stalker.server.model.api;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class UserDto {
  @Id
  Long id;

  @NonNull
  UserDataDto userData;

}

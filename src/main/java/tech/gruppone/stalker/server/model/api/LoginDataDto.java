package tech.gruppone.stalker.server.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDataDto {

  @NonNull String email;

  @NonNull String password;
}

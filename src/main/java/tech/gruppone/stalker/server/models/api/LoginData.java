package tech.gruppone.stalker.server.models.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class LoginData {

  @NonNull
  String email;

  @NonNull
  String password;

}

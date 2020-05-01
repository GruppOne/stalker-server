package tech.gruppone.stalker.server.models.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import tech.gruppone.stalker.server.models.api.LoginData;
import tech.gruppone.stalker.server.models.api.UserData;

@Data
@NoArgsConstructor
public class PutUserIdRequest {

  @NonNull
  UserData userData;

  @NonNull
  LoginData loginData;

}

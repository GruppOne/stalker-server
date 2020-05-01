package tech.gruppone.stalker.server.model.api.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import tech.gruppone.stalker.server.model.api.LoginData;
import tech.gruppone.stalker.server.model.api.UserData;

@Data
@NoArgsConstructor
public class PostUsersRequest {

  @NonNull
  LoginData loginData;

  @NonNull
  UserData userData;

}

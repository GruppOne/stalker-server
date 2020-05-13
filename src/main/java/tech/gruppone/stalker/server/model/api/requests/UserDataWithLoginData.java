package tech.gruppone.stalker.server.model.api.requests;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import tech.gruppone.stalker.server.model.api.LoginDataDto;
import tech.gruppone.stalker.server.model.api.UserDataDto;

@Builder
@Data
public class UserDataWithLoginData {

  @NonNull
  LoginDataDto loginData;

  @NonNull
  UserDataDto userData;

}

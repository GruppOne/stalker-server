package tech.gruppone.stalker.server.model.api;

import lombok.Value;

@Value
public class LoginDataDto {

  String email;

  String password;
}

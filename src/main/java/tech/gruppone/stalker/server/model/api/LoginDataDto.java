package tech.gruppone.stalker.server.model.api;


import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class LoginDataDto {

   String email;

  String password;
}

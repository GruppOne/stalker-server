package tech.gruppone.stalker.server.models.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class LdapConfiguration {

  @NonNull
  String host;

  @NonNull
  String username;

  @NonNull
  String password;

}

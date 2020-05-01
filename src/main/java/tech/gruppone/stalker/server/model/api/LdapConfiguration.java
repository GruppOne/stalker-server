package tech.gruppone.stalker.server.model.api;

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

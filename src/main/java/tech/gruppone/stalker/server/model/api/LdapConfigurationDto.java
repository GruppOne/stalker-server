package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class LdapConfigurationDto {

  @NonNull String username;

  @NonNull String password;
}

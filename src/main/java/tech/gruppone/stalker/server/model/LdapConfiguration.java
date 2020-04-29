package tech.gruppone.stalker.server.model;

import java.net.URL;
import org.springframework.data.annotation.Id;
import lombok.NonNull;
import lombok.Value;

@Value
public class LdapConfiguration {

  @Id
  @NonNull
  Long id;

  @NonNull
  URL host;

  @NonNull
  String username;

  @NonNull
  String password;

}

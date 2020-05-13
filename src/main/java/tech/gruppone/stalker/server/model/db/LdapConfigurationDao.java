package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Value
public class LdapConfigurationDao {
  @Id
  @Column("organizationId")
  long organizationId;

  @Column("host")
  @NonNull
  String host;

  @Column("username")
  @NonNull
  String username;

  @Column("password")
  @NonNull
  String password;
}

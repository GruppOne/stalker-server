package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("LdapConfiguration")
public class LdapConfigurationDao {
  @Id
  @Column("organizationId")
  Long organizationId;

  @Column("url")
  @NonNull
  String url;

  @Column("username")
  @NonNull
  String username;

  @Column("password")
  @NonNull
  String password;
}

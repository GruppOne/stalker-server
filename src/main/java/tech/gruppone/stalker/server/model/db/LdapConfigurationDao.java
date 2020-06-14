package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("LdapConfiguration")
public class LdapConfigurationDao {

  @Id
  @Column("organizationId")
  Long organizationId;

  @Column("url")
  @NonNull
  String url;

  @Column("baseDn")
  @NonNull
  String baseDn;

  @Column("bindDn")
  @NonNull
  String bindDn;

  @Column("bindPassword")
  @NonNull
  String bindPassword;
}

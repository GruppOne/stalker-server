package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("LdapConfiguration")
public class LdapConfigurationDao {

  @With
  @Id
  @Column("id")
  Long id;

  @Column("organizationId")
  @NonNull
  Long organizationId;

  @Column("url")
  @NonNull
  String url;

  @Column("baseDn")
  @NonNull
  String baseDn;

  @Column("bindRdn")
  @NonNull
  String bindRdn;

  @Column("bindPassword")
  @NonNull
  String bindPassword;
}

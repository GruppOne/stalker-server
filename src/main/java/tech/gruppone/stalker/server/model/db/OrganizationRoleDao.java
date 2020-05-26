package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import tech.gruppone.stalker.server.model.AdministratorType;

@Builder
@Value
@Table("OrganizationRole")
public class OrganizationRoleDao {

  @Id
  @With
  @Column("id")
  Long id;

  @NonNull
  @Column("organizationId")
  Long organizationId;

  @NonNull
  @Column("userId")
  Long userId;

  @NonNull
  @Column("administratorType")
  AdministratorType administratorType;

  @Column("createdDate")
  LocalDateTime createdDate;
}

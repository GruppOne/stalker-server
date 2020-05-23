package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("OrganizationRole")
public class OrganizationRoleDao {

  @NonNull
  @Column("organizationId")
  Long organizationId;

  @NonNull
  @Column("userId")
  Long userId;

  @Id
  @Column("administratorType")
  Long administratorType;

  String name;

  @NonNull
  @Column("createdDate")
  LocalDateTime createdDate;
}

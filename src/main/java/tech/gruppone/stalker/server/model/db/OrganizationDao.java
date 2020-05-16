package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("Organization")
public class OrganizationDao {
  // r2dbc wants a null field when saving the DAO
  @With
  @Id
  @Column("id")
  Long id;

  @NonNull
  @Column("name")
  String name;

  @NonNull
  @Column("description")
  String description;

  @Builder.Default
  @Column("isPrivate")
  Boolean isPrivate = false;

  @Column("createdDate")
  LocalDateTime createdDate;

  @With
  @Column("lastModifiedDate")
  LocalDateTime lastModifiedDate;
}

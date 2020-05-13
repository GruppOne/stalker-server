package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("Organization")
public class OrganizationDao {
  @Id
  @Column("id")
  long id;

  @NonNull
  @Column("name")
  String name;

  @NonNull
  @Column("description")
  String description;

  @Column("isPrivate")
  boolean isPrivate;

  @NonNull
  @Column("createdDate")
  LocalDateTime createdDate;

  @NonNull
  @Column("lastModifiedDate")
  LocalDateTime lastModifiedDate;
}

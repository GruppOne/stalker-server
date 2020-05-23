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
@Table("Connection")
public class ConnectionDao {

  @Id
  @With
  @Column("id")
  Long id;

  @NonNull
  @Column("userId")
  Long userId;

  @NonNull
  @Column("organizationId")
  Long organizationId;

  @With
  @Column("createdDate")
  LocalDateTime createdDate;
}

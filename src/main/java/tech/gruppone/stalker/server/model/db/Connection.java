package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection {

  @NonNull
  @Column("organizationId")
  Long organizationId;

  @NonNull
  @Column("userId")
  Long userId;

  @NonNull
  @Column("createdDate")
  LocalDateTime createdDate;
}

package tech.gruppone.stalker.server.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("Connection")
public class Connection {

   @Column("organizationId")
   Long organizationId;

   @Column("userId")
   Long userId;

   @Column("createdDate")
   LocalDateTime createdDate;
}

package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Builder
// TODO try with @value
@Data
@Table("Connection")
public class ConnectionDao {

   @Column("organizationId")
   Long organizationId;

   @Column("userId")
   Long userId;

   @Column("createdDate")
   LocalDateTime createdDate;
}

package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

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

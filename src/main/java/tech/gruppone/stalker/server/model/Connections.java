package tech.gruppone.stalker.server.model;

import java.time.LocalDateTime;
import javax.annotation.sql.DataSourceDefinition;
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
public class Connections {

    @NonNull
    @Column("organizationId")
    Integer organizationId;

    @NonNull
    @Column("userId")
    Integer userid;

    @NonNull
    LocalDateTime createdDate;
}

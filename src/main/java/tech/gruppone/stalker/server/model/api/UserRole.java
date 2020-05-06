package tech.gruppone.stalker.server.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {


    @Column("organizationId")
    private Integer organizationId;

    private String role;
}

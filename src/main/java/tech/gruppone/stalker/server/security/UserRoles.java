package tech.gruppone.stalker.server.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {

;
    private Long organizationId;

    private String role;
}

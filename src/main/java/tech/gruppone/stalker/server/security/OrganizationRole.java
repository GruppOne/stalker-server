package tech.gruppone.stalker.server.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRole {

    private long organizationId;
    private String role;
}

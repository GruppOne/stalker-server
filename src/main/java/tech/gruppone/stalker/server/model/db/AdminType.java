package tech.gruppone.stalker.server.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminType {

    private String role;
    private String name;
}

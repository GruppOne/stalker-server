package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("AdministratorType")
public class AdministratorTypeDao {

  @Column("id")
  Long id;

  @Column("name")
  String name;

  @Column("role")
  String role;
}

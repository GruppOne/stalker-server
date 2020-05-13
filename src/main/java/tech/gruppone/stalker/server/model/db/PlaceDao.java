package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("Place")
public class PlaceDao {

  @Id
  @Column("id")
  long id;

  @Column("organizationId")
  long organizationId;

  @NonNull
  @Column("name")
  String name;

  // TODO serialize polygon (to a new class?)
}

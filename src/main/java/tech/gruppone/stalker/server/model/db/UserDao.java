package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("User")
public class UserDao {
  @Id
  @Column("id")
  long id;

  @NonNull
  @Column("email")
  String email;

  @NonNull
  @Column("password")
  String password;
}

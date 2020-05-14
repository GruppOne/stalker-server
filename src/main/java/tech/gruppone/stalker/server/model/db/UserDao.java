package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("User")
public class UserDao {
  @Id
  @Column("id")
  Long id;

  @NonNull
  @Column("email")
  String email;

  @NonNull
  @Column("password")
  String password;
}

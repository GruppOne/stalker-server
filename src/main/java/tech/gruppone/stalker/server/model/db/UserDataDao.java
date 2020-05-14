package tech.gruppone.stalker.server.model.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table("UserData")
public class UserDataDao {

  @Id
  @Column("userId")
  long userId;

  @NonNull
  @Column("firstName")
  String firstName;

  @NonNull
  @Column("lastName")
  String lastName;

  @NonNull
  @Column("birthDate")
  LocalDate birthDate;

  @NonNull
  @Column("createdDate")
  LocalDateTime createdDate;

  @NonNull
  @Column("lastModifiedDate")
  LocalDateTime lastModifiedDate;
}

package tech.gruppone.stalker.server.model.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("UserData")
public class UserDataDao {

  @With
  @Id
  @NonNull
  @Column("userId")
  Long userId;

  @NonNull
  @Column("firstName")
  String firstName;

  @NonNull
  @Column("lastName")
  String lastName;

  @NonNull
  @Column("birthDate")
  LocalDate birthDate;

  @Column("createdDate")
  LocalDateTime createdDate;

  @With
  @Column("lastModifiedDate")
  LocalDateTime lastModifiedDate;
}

package tech.gruppone.stalker.server.model.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.influxdb.annotation.Column;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.NonNull;
import lombok.Data;

@Builder
@Data
public class UserDataDao {

  @Id
  @Column(name = "userId")
  Long userId;

  @NonNull
  @Column(name = "firstName")
  String firstName;

  @NonNull
  @Column(name = "lastName")
  String lastName;

  @NonNull
  @Column(name = "birthDate")
  LocalDate birthDate;

  @NonNull
  @Column(name = "createdDate")
  LocalDateTime createdDate;

  @NonNull
  @Column(name = "lastModifiedDate")
  LocalDateTime lastModifiedDate;

}

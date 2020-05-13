package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.influxdb.annotation.Column;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class UserDao {
  @Id
  @Column(name = "id")
  Long id;

  @NonNull
  @Column(name = "email")
  String email;

  @NonNull
  @Column(name = "password")
  String password;
}

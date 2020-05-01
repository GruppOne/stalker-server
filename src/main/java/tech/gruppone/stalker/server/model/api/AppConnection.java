package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AppConnection {

  @NonNull
  Long organizationId;

  @NonNull
  Long userId;

  //LocalDateTime
  Timestamp creationDateTime;

}

package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AppConnection {

  @Id
  Long organizationId;

  @NonNull
  Long userId;

}

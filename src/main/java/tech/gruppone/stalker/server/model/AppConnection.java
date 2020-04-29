package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.NonNull;
import lombok.Value;

@Value
public class AppConnection {

  @Id
  @NonNull
  Long organizationId;

  @NonNull
  Long userId;

}

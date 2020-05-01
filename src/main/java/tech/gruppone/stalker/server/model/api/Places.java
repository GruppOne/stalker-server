package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Places {

  @NonNull
  Long placeId;

  @NonNull
  Integer usersInside;

}

package tech.gruppone.stalker.server.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Polygon {

  @NonNull
  Double latitude;

  @NonNull
  Double longitude;

}

package tech.gruppone.stalker.server.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CurrentPlaces {

  @NonNull
  Double timeInside;

  @NonNull
  Long organizationId;

  @NonNull
  Long placeId;

}

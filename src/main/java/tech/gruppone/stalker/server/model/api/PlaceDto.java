package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class PlaceDto {

  long id;

  @NonNull PlaceDataDto data;
}

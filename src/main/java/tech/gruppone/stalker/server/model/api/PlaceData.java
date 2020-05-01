package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class PlaceData {

  @NonNull
  String name;

  @Singular
  @NonNull
  List<Polygon> polygon;

  @NonNull
  PlaceInfo placeInfo;

}

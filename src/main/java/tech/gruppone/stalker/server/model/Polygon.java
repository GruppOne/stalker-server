package tech.gruppone.stalker.server.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class Polygon {

  // We discussed how leaflet and SQL manage polygons. We decided that we need to keep the first and last point identical.
  @Singular
  @NonNull
  List<GeographicalPoint> geographicalPoints;


}

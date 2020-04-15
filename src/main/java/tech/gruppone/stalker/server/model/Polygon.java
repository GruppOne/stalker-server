package tech.gruppone.stalker.server.model;

import java.util.Set;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
public class Polygon {

  //  TODO can this be a flux?
  @Singular
  Set<GeographicalPoint> geographicalPoints;

  @Value
  public static class GeographicalPoint {

    double latitude;
    double longitude;
  }
}

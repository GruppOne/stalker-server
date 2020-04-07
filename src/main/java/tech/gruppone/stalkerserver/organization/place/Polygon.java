package tech.gruppone.stalkerserver.organization.place;

import java.util.Set;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
public class Polygon {

  @Singular
  Set<GeographicalPoint> geographicalPoints;

  @Value
  public static class GeographicalPoint {

    double latitude;
    double longitude;
  }
}

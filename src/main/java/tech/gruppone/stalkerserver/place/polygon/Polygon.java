package tech.gruppone.stalkerserver.place.polygon;

import java.util.Set;
import lombok.Value;

@Value
public class Polygon {
  Set<GeographicalPoint> geographicalPoints;

  @Value
  public static class GeographicalPoint{
    double latitude;
    double longitude;
  }
}

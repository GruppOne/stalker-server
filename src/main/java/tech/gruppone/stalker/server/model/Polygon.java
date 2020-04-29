package tech.gruppone.stalker.server.model;

import java.util.Set;

import lombok.Singular;
import lombok.Value;


public class Polygon {

  //  TODO can this be a flux?
  @Singular
  Set<GeographicalPoint> geographicalPoints;

  @Value
  public static class GeographicalPoint {
    double latitude;
    double longitude;
  }

  // FIXME this cannot be a set. when we discussed how leaflet and SQL manage polygons we decided that we need to keep the first and last point identical, which means it needs to be a list (no duplicated elements in a set). Plus, the points need to be ordered and a set isn't ordered
  public Set<GeographicalPoint> getGeographicalPoints(){
    return this.geographicalPoints;
  }
  public void setGeographicalPoints(Set<GeographicalPoint> geographicalPoints){
    this.geographicalPoints = geographicalPoints;
  }
}

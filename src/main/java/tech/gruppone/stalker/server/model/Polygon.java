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

  public Set<GeographicalPoint> getGeographicalPoints(){
    return this.geographicalPoints;
  }
  public void setGeographicalPoints(Set<GeographicalPoint> geographicalPoints){
    this.geographicalPoints = geographicalPoints;
  }
}

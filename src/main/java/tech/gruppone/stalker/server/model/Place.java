package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

public class Place {

  @Id
  Long id;
  @NonNull String name;
  //@NonNull Polygon polygon;
  //@NonNull PlaceData placeData;



  @Data
  public static class PlaceData {

    String address;
    String city;
    String zipCode;
    String state;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }



/*
  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }

  public PlaceData getPlaceData() {
    return placeData;
  }

  public void setPlaceData(PlaceData placeData) {
    this.placeData = placeData;
  }
*/
}

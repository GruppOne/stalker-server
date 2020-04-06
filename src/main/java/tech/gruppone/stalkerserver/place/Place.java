package tech.gruppone.stalkerserver.place;

import lombok.Getter;
import lombok.Value;
import tech.gruppone.stalkerserver.place.polygon.Polygon;

public class Place {
  @Getter
  private final int id;
  @Getter
  private String name;
  @Getter
  private Polygon polygon;
  @Getter
  private PlaceData placeData;

  public Place(int id, String name, Polygon polygon, PlaceData placeData) {
    this.id = id;
    this.name = name;
    this.polygon = polygon;
    this.placeData = placeData;
  }

  @Value
  public static class PlaceData{
    private String address;
    private String city;
    private String zipCode;
    private String state;
  }

}

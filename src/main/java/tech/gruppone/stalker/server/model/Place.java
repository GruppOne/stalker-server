package tech.gruppone.stalker.server.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Place {

  int id;
  @NonNull String name;
  @NonNull Polygon polygon;
  @NonNull PlaceData placeData;

  @Builder
  @Data
  public static class PlaceData {

    String address;
    String city;
    String zipCode;
    String state;
  }

}

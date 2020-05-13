package tech.gruppone.stalker.server.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Builder
@Value
public class Place {

  @Id
  Long id;
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

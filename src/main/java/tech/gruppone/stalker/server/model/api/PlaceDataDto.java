package tech.gruppone.stalker.server.model.api;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class PlaceDataDto {

  @NonNull String name;

  // this does not need @Singular. We always treat polygons as a whole entity
  @NonNull List<GeographicalPoint> polygon;
  @NonNull PlaceInfo placeInfo;

  @Value
  public static class GeographicalPoint {
    double latitude;
    double longitude;
  }

  @Builder
  @Value
  public static class PlaceInfo {

    @NonNull String address;

    @NonNull String city;

    @NonNull String zipcode;

    @NonNull String state;
  }
}

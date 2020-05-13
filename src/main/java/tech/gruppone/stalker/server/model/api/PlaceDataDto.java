package tech.gruppone.stalker.server.model.api;

import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class PlaceDataDto {

  @NonNull String name;

  @NonNull List<GeographicalPoint> polygon;
  @NonNull PlaceInfo placeInfo;

  public static class GeographicalPoint {
    double latitude;
    double longitude;
  }

  public static class PlaceInfo {

    @NonNull String address;

    @NonNull String city;

    @NonNull String zipcode;

    @NonNull String state;
  }
}

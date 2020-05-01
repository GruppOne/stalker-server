package tech.gruppone.stalker.server.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PlaceInfo {

  @NonNull
  String address;

  @NonNull
  String city;

  @NonNull
  String zipcode;

  @NonNull
  String state;

}

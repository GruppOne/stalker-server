package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Place {

  @Id
  Long id;

  @NonNull
  String name;

  // @NonNull Polygon polygon;

  @NonNull
  String address;

  @NonNull
  String city;

  @NonNull
  String zipcode;

  @NonNull
  String state;

  @NonNull
  String position;

}

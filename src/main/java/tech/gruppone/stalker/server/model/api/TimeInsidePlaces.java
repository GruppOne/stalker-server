package tech.gruppone.stalker.server.model.api;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class TimeInsidePlaces {

  @Singular
  @NonNull
  List<CurrentPlaces> currentPlaces;

}

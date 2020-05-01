package tech.gruppone.stalker.server.models.api;

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
  PlaceData data;

}

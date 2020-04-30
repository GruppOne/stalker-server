package tech.gruppone.stalker.server.api.resources;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MultiLocationInfo {
  @NonNull
  private Timestamp timestamp;

  @NonNull
  private Long userId;

  @NonNull
  private Boolean anonymous;

  @NonNull
  private Boolean inside;

  @NonNull
  List<Long> placeIds = new ArrayList<>();

}

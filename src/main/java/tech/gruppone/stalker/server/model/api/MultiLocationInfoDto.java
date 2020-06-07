package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class MultiLocationInfoDto {
  @NonNull Timestamp timestamp;

  @NonNull String userType;

  // this is redundant. the information is included in the SecurityContext
  @NonNull String userId;

  @NonNull Boolean inside;

  @NonNull List<Long> placeIds;
}

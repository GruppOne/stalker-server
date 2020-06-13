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

  // can only be the string "known" or "anonymous"
  @NonNull String userType;

  @NonNull String userId;

  @NonNull Boolean inside;

  @NonNull List<Long> placeIds;

  public boolean isAnonymous() {
    return this.userType.equals("anonymous");
  }
}

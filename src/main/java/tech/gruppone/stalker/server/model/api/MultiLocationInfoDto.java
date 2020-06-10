package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

// TODO check if it works as expected
@Builder
@Value
public class MultiLocationInfoDto {
  @NonNull Timestamp timestamp;

  // can only be "known" or "anonymous"
  @NonNull String userType;

  // this is redundant. the information is included in the SecurityContext
  @NonNull String userId;

  @NonNull Boolean inside;

  @NonNull List<Long> placeIds;

  public boolean isAnonymous() {
    return this.userType.equals("anonymous");
  }
}

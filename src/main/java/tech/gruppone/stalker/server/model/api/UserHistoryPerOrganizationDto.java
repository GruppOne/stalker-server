package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class UserHistoryPerOrganizationDto {
  @NonNull Long userId;
  @NonNull List<PlaceHistoryDto> history;

  @Builder
  @Value
  public static class PlaceHistoryDto {
    @NonNull Timestamp timestamp;
    @NonNull Long placeId;
    @NonNull Boolean inside;
  }
}

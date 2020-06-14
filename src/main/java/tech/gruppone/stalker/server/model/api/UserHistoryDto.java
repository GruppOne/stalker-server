package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.NonNull;
import lombok.Value;

@Value
public class UserHistoryDto {
  @NonNull List<OrganizationHistoryDto> history;

  @Value
  public static class OrganizationHistoryDto {
    @NonNull Long organizationId;
    @NonNull UserHistoryPerOrganizationDto historyPerOrganization;
  }
}

package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Builder
@Value
public class OrganizationDto {
  @Id long id;

  @NonNull OrganizationDataDto data;

  @NonNull List<Long> placeIds;
}

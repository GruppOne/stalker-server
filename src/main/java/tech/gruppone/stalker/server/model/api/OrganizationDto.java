package tech.gruppone.stalker.server.model.api;

import java.util.List;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class OrganizationDto {
  @Id long id;

  @NonNull OrganizationDataDto data;

  @NonNull List<Long> placeIds;
}

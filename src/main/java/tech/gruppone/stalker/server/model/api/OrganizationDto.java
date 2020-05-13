package tech.gruppone.stalker.server.model.api;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class OrganizationDto {
  @Id long id;

  @NonNull OrganizationDataDto organizationData;
}

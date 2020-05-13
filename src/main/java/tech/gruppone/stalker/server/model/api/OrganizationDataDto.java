package tech.gruppone.stalker.server.model.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Builder
@Value
public class OrganizationDataDto {

  @NonNull String name;

  @NonNull String description;

  // TODO implement ldapConfiguration

  @NonNull @Singular List<PlaceDto> places = new ArrayList<>();

  @NonNull LocalDateTime creationDateTime;

  @NonNull LocalDateTime lastChangeDateTime;
}

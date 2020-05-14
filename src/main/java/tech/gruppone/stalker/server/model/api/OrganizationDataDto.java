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

  // TODO implement ldapConfiguration. might want to use inheritance instead of nullable fields

  @NonNull
  @Singular("place")
  List<PlaceDto> places = new ArrayList<>();

  @Builder.Default boolean isPrivate = false;

  @NonNull LocalDateTime creationDateTime;

  @NonNull LocalDateTime lastChangeDateTime;
}

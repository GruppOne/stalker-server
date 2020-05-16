package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
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

  @NonNull @Singular List<PlaceDto> places;

  // Using the boxed type because both jackson and lombok behave strangely with primitive booleans
  @NonNull @Builder.Default Boolean isPrivate = false;

  // use timestamps in DTOs to serialize datetimes correctly
  Timestamp creationDateTime;
  Timestamp lastChangeDateTime;
}

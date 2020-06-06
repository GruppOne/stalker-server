package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class OrganizationDataDto {

  @NonNull String name;

  @NonNull String description;

  @NonNull @Builder.Default String organizationType = "public";

  // TODO implement ldapConfiguration. might want to use inheritance instead of nullable fields

  // use timestamps in DTOs to serialize datetimes correctly
  Timestamp creationDateTime;
  Timestamp lastChangeDateTime;
}

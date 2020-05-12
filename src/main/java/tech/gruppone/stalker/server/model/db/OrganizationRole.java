package tech.gruppone.stalker.server.model.db;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import tech.gruppone.stalker.server.model.api.AdministratorType;

// FIXME does not work with repository
@Builder
@Value
@AllArgsConstructor
public class OrganizationRole {

  long organizationId;
  long userId;
  @NonNull
  AdministratorType administratorType;
  Timestamp createdDate;
}

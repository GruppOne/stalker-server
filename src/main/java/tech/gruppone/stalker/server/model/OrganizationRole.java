package tech.gruppone.stalker.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@AllArgsConstructor
@Table("OrganizationRole")
public class OrganizationRole {

  @NonNull  String organizationId;
  @NonNull String userId;
  @NonNull String name;
  @NonNull String createdDate;

}

package tech.gruppone.stalker.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRole {

  @NonNull  Long organizationId;
  @NonNull Long userId;
  @NonNull String name;
  @NonNull String createdDate;

}

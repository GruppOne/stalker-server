package tech.gruppone.stalker.server.model.api;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Organization {

  @Id
  Long id;

  @NonNull
  OrganizationData data;

}

package tech.gruppone.stalker.server.model.api.responses;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class UsersInsideOrganization {

  @NonNull
  Integer usersInside;

  @Singular
  @NonNull
  List<Places> places;

}

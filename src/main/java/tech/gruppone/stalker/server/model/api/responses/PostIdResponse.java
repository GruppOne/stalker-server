package tech.gruppone.stalker.server.model.api.responses;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

// Same response used for POST /users and POST /organizations

@Builder
@Data
public class PostIdResponse {

  @NonNull
  Long id;

}

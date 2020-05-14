package tech.gruppone.stalker.server.model.api;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EncodedJwtDto {

  String encodedJwt;
}

package tech.gruppone.stalker.server.model.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class UpdatePasswordDto {

  @NonNull
   String oldPassword;

  @NonNull
   String newPassword;
}

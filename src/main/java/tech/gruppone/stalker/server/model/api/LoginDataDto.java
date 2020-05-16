package tech.gruppone.stalker.server.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@Data
@Value
public class LoginDataDto {

  String email;

  String password;
}

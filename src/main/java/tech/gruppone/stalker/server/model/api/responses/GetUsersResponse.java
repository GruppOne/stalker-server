package tech.gruppone.stalker.server.model.api.responses;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import tech.gruppone.stalker.server.model.api.UserDto;

@Builder
@Data
public class GetUsersResponse {

  List<UserDto> users;
}

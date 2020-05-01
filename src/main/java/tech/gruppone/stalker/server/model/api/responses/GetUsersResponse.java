package tech.gruppone.stalker.server.models.api.response;

import java.util.List;

import lombok.Value;
import tech.gruppone.stalker.server.models.api.User;

@Value
public class GetUsersResponse {
  // TODO write me

  List<User> users;
}

package tech.gruppone.stalker.server.model.api.responses;

import java.util.List;

import lombok.Value;
import tech.gruppone.stalker.server.model.api.User;

@Value
public class GetUsersResponse {
  // TODO write me

  List<User> users;
}

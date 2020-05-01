package tech.gruppone.stalker.server.model.api;

import java.util.List;

import lombok.Value;
import tech.gruppone.stalker.server.model.User;

@Value
public class GetUsersResponse {
  // TODO write me

  List<User> users;
}

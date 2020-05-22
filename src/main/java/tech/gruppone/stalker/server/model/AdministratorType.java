package tech.gruppone.stalker.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// XXX ensure the numbers match the ids in the DB table AdministratorType!
public enum AdministratorType {
  @JsonProperty("Admin")
  ADMIN(1),
  @JsonProperty("Owner")
  OWNER(2),
  @JsonProperty("Manager")
  MANAGER(3),
  @JsonProperty("Viewer")
  VIEWER(4);

  private final int roleKey;

  private AdministratorType(final int roleKey) {
    this.roleKey = roleKey;
  }

  public int getRoleKey() {
    return this.roleKey;
  }
}

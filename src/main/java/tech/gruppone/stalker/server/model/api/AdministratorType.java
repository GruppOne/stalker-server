package tech.gruppone.stalker.server.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO find a better way to represent this (too much duplication)
public enum AdministratorType {
  @JsonProperty("Admin")
  ADMIN("Admin"),
  @JsonProperty("Owner")
  OWNER("Owner"),
  @JsonProperty("Manager")
  MANAGER("Manager"),
  @JsonProperty("Viewer")
  VIEWER("Viewer");

  private final String textRepresentation;

  private AdministratorType(String textRepresentation) {
  this.textRepresentation = textRepresentation;
  }

  @Override
  public String toString() {
  return textRepresentation;
  }
}

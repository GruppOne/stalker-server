package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.NonNull;

// Model created to reflect exactly keys of the json which api receives by app and web app
public class ConnectedOrganization {

  @Id
  @NonNull
  private Long id;
  @NonNull
  private String name;
  @NonNull
  private String description;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}

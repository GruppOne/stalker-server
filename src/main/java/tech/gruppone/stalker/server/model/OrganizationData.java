package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.NonNull;
import lombok.Value;

// Model created to reflect exactly keys of the json which api receives by app and web app
@Value
public class OrganizationData {

  @Id
  @NonNull
  Long id;

  @NonNull
  String name;

  @NonNull
  String description;

}

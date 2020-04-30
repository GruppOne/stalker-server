package tech.gruppone.stalker.server.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

// Model created to reflect exactly keys of the json which api receives by app and web app
// Endpoint: /user/{id}/organizations/connections
// PAY ATTENTION: it could be redundant
@Data
@NoArgsConstructor
public class OrganizationData {

  @Id
  List<Long> connectedOrganizations;

}

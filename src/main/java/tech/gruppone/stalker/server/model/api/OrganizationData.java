package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class OrganizationData {

  @NonNull
  String name;

  @NonNull
  String description;

  LdapConfiguration ldapConfiguration;

  @Singular
  List<Place> places = new ArrayList<Place>();

  @NonNull
  Boolean isPrivate;

  Timestamp creationDateTime;

  Timestamp lastChangeDateTime;

}

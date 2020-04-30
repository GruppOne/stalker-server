package tech.gruppone.stalker.server.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
public class Organization {

  @Id
  private Long id;

  @NonNull
  private String name;

  @NonNull
  private String description;

  @Singular
  private List<Place> places = new ArrayList<Place>();

  // LdapConfiguration ldapConfiguration;

  boolean isPrivate;

}

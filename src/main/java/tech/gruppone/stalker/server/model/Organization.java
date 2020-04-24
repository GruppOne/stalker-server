package tech.gruppone.stalker.server.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

// TODO should builder be removed? do we use it anywhere but in tests?
@Builder
@Value
public class Organization {

  @Id
  Long id;
  @NonNull String name;
  @Builder.Default
  @NonNull String description = "";

  @Singular
  List<Place> places = new ArrayList<Place>();

  @Builder.Default
  boolean isPrivate = false;
//  LdapConfiguration ldapConfiguration;

//  @Builder
//  @Data
//  public static class LdapConfiguration {
//
//    @NonNull
//    private final URL host;
//    @NonNull
//    private final String username;
//    @NonNull
//    private final String password;
//  }

//  Timestamp createdDate;
//  Timestamp lastModifiedDate;

}

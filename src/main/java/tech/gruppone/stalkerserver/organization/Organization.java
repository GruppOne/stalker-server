package tech.gruppone.stalkerserver.organization;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import tech.gruppone.stalkerserver.organization.place.Place;

@Builder
@Value
public class Organization {

  int id;
  @NonNull String name;
  @Builder.Default
  @NonNull String description = "";

  @Singular
  Set<Place> places = new HashSet<Place>();

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

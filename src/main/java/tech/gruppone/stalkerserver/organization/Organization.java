package tech.gruppone.stalkerserver.organization;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import tech.gruppone.stalkerserver.organization.place.Place;

@Builder
@Value
@AllArgsConstructor
public class Organization {

  int id;
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

package tech.gruppone.stalker.server.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

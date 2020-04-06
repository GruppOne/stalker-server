package tech.gruppone.stalkerserver.organization;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import tech.gruppone.stalkerserver.place.Place;

public class Organization {

  @Getter
  @Setter
  private String name;
  @Getter
  private String description;
  @Getter
  private LdapConfiguration ldapConfiguration;
  // Is it final?
  @Getter
  private Set<Place> places = new HashSet<Place>();
  @Getter
  private boolean isPrivate;
  @Getter
  private final LocalDateTime createdDate;
  @Getter
  @Setter
  private LocalDateTime lastModifiedDate;

  public Organization(int id, String name, String description,
    LdapConfiguration ldapConfiguration, Set<Place> places, boolean isPrivate, LocalDateTime createdDate,
    LocalDateTime lastModifiedDate) {
    this.name = name;
    this.description = description;
    this.ldapConfiguration = ldapConfiguration;
    this.places = places;
    this.isPrivate = isPrivate;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  public static class LdapConfiguration {
    @Getter
    private String host;
    @Getter
    private String username;
    @Getter
    private String password;
  }
}

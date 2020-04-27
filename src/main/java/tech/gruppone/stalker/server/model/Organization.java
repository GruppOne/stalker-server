package tech.gruppone.stalker.server.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO should builder be removed? do we use it anywhere but in tests?

public class Organization {

  @Id
  private Long id;
  @NonNull
  private String name;
  @NonNull
  private String description;

  @Singular
  private List<Place> places = new ArrayList<Place>();

  boolean isPrivate;

  public Long getId(){
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }

  public String getDescription(){
    return this.description;
  }

  public void setDescription(String description){
    this.description = description;
  }


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

package tech.gruppone.stalker.server.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import lombok.NonNull;
import lombok.Singular;

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

}

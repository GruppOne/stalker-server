package tech.gruppone.stalker.server.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.springframework.data.annotation.Id;
import lombok.NonNull;

// @Builder
// @Value
public class User {

  @Id
  @NonNull
  private Long id;

  @NonNull
  private String email;

  @NonNull
  private String password;

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  @NonNull
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate birthDate;
  // Timestamp createdDate;
  // Timestamp lastModifiedDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }


  // // TODO re-add userData property
  // // @NonNull
  // // UserData userData;

  // @Builder
  // @Value
  // public static class UserData {
  //   @NonNull
  //   String email;

  //   @NonNull
  //   String password;

  //   String firstName;
  //   String lastName;
  //   // TODO use timezone-agnostic type for dates
  //   LocalDate birthDate;
  //   // Timestamp createdDate;
  //   // Timestamp lastModifiedDate;
  // }

}

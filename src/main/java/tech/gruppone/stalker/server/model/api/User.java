package tech.gruppone.stalker.server.model.api;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class User {

  @Id
  Long id;

  @NonNull
  UserData userData;

  // CONSTRUCTOR NEEDED FOR GET /users --> TRY IT TO WORK IT!!
  // public User(@NonNull Long id, @NonNull String email, @NonNull String firstName, @NonNull String lastName, @NonNull LocalDate birthDate, @NonNull Timestamp createdDate) {

  //   this.id = id;
  //   this.userData.email = email;
  //   this.userData.firstName = firstName;
  //   this.userData.lastName = lastName;
  //   this.userData.birthDate = birthDate;
  //   this.userData.createdDate = createdDate;

  // }

}

package tech.gruppone.stalkerserver.user;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {

  int id;
  String email;
  String password;
  UserData userData;

  @Builder
  @Value
  public static class UserData {

    String firstName;
    String lastName;
    LocalDate birthDate;
//    Timestamp createdDate;
//    Timestamp lastModifiedDate;
  }

}

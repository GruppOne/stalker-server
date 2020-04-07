package tech.gruppone.stalkerserver.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

  int id;
  //UserData userData;
  String email;
  String password;

  /*@Builder
  @Data
  public static class UserData {

    String firstName;
    String lastName;
    LocalDate birthDate;
    Timestamp createdDate;
    Timestamp lastModifiedDate;
  }*/
}

package tech.gruppone.stalker.server.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Builder
@Value
public class User {

  @Id
  @NonNull
  Long id;

  @NonNull
  String email;

  @NonNull
  String password;

  // TODO re-add userData property
  // @NonNull
  // UserData userData;

  @Builder
  @Value
  public static class UserData {

    String firstName;
    String lastName;
    // TODO use timezone-agnostic type for dates
    LocalDate birthDate;
    // Timestamp createdDate;
    // Timestamp lastModifiedDate;
  }

}

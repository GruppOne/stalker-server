package tech.gruppone.stalkerserver.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class User {

  @Getter
  private final int id;
  @Getter
  private String email;
  @Getter
  @Setter
  private String password;
  @Getter
  private final UserData userData;

  public User(int id, String email, String password, UserData userData) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.userData = userData;
  }

  @Builder
  @AllArgsConstructor
  public static class UserData {

    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private LocalDate birthDate;
    @Getter
    private final LocalDateTime createdDate;
    @Getter
    private LocalDateTime lastModifiedDate;

    public UserData(String firstName, String lastName, LocalDate birthDate, LocalDateTime createdDate,
      LocalDateTime lastModifiedDate) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
      this.createdDate = createdDate;
      this.lastModifiedDate = lastModifiedDate;
    }
    
  }
}

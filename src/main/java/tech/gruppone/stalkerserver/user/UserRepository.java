package tech.gruppone.stalkerserver.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserRepository {

  private User user = User.builder().email("ciao").password("password").build();

  public Boolean findByEmail(String email) {
    return user.getEmail() == email;
  }

}

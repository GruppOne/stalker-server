package tech.gruppone.stalkerserver.user;

import tech.gruppone.stalkerserver.user.User.UserData;

public class UserRepository {

  private User user;

  public UserRepository(User user) {
      this.user = new User(1, "mariorossi@gmail.com", "password", new UserData("mario", "rossi",,1,1));
  }

  public Boolean findByEmail(String email){
    return user.getEmail()== email;
  }

  public Boolean finByPassword(String password){
    return user.getPassword() == password;
  }

}

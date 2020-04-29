package tech.gruppone.stalker.server.model;

import java.net.URL;
import org.springframework.data.annotation.Id;
import lombok.NonNull;

public class LdapConfiguration {

  @Id
  @NonNull
  private Long id;
  @NonNull
  private URL host;
  @NonNull
  private String username;
  @NonNull
  private String password;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public URL getHost() {
    return this.host;
  }

  public void setHost(URL host) {
    this.host = host;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

}

package tech.gruppone.stalker.server.configuration;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class JwtConfiguration {

  @Value("${jwt.secret}")
  String encodedKey;

  @Value("${jwt.expiration-time}")
  String expirationTime;

  /*public JwtConfiguration(
      @Value("${jwt.secret}") String encodedKey,
    @Value("${jwt.expiration-time}") String expirationTime) {
    this.encodedKey = Base64.getEncoder().encodeToString(encodedKey.getBytes());
    this.expirationTime = expirationTime;
  }*/

  public String getExpirationTime() {
    return expirationTime;
  }

  public Key getEncodedKey() {
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }
}

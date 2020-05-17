package tech.gruppone.stalker.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtConfiguration {

  String encodedKey;
  String expirationTime;

  public JwtConfiguration(
      @NonNull @Value("${jwt.secret}") String encodedKey,
      @NonNull @Value("${jwt.expiration-time}") String expirationTime) {
    this.encodedKey = Base64.getEncoder().encodeToString(encodedKey.getBytes());
    this.expirationTime = expirationTime;
  }

  private Key getEncodedKey() {
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }

  // TODO should probably move the following methods to a separate class called JwtTokenService

  public Claims getJWTString(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getEncodedKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /*public Boolean isTokenSigned(String token) {
    return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().isSigned(token);
  }*/

  public String getUserId(String token) {
    return getJWTString(token).getSubject();
  }

  /*public Date getExpirationDate(String token) {
    return getJWTString(token).getExpiration();
  }

  public boolean isTokenExpired(String token) {
    Date date = new Date();
    return getExpirationDate(token).before(date);
  }*/

  public String createToken(Long id) {
    Date issuedAt = new Date();
    Date expirationAt = new Date(issuedAt.getTime() + Long.parseLong(expirationTime) * 1000000);
    return Jwts.builder()
        .setSubject(String.valueOf(id))
        .setIssuedAt(issuedAt)
        .setExpiration(expirationAt)
        .setId(UUID.randomUUID().toString())
        .signWith(getEncodedKey())
        .compact();
  }
}

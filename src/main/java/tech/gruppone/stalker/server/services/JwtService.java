package tech.gruppone.stalker.server.services;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tech.gruppone.stalker.server.configuration.JwtConfiguration;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {

  JwtConfiguration jwtConfiguration;

  // TODO should probably move the following methods to a separate class called JwtTokenService

  // public Claims getJWTString(String token) {
  //   return Jwts.parserBuilder()
  //       .setSigningKey(getEncodedKey())
  //       .build()
  //       .parseClaimsJws(token)
  //       .getBody();
  // }

  /*public Boolean isTokenSigned(String token) {
    return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().isSigned(token);
  }*/

  // public String getUserId(String token) {
  //   return getJWTString(token).getSubject();
  // }

  /*public boolean isTokenExpired(String token) {
    Date date = new Date();
    return getExpirationDate(token).before(date);
  }*/

  public String createToken(Long id) {
    Date issuedAt = new Date();
    Date expirationAt =
        new Date(
            issuedAt.getTime() + Long.parseLong(jwtConfiguration.getExpirationTime()) * 1000000);
    return Jwts.builder()
        .setSubject(String.valueOf(id))
        .setIssuedAt(issuedAt)
        .setExpiration(expirationAt)
        .setId(UUID.randomUUID().toString())
        .signWith(jwtConfiguration.getEncodedKey())
        .compact();
  }
}

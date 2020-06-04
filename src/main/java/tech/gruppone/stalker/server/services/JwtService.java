package tech.gruppone.stalker.server.services;

import io.jsonwebtoken.Jwts;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tech.gruppone.stalker.server.configuration.JwtConfiguration;

@Log4j2
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {
  Clock clock;
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
    // we use this syntax because it's actually testable
    Date issuedAt = Date.from(Instant.now(clock));
    Date expirationAt =
        new Date(
            issuedAt.getTime() + Long.parseLong(jwtConfiguration.getExpirationTime()) * 1000000);

    log.info("Generating JWT with id={}, issuedAt={}", id, issuedAt);

    return Jwts.builder()
        .setSubject(String.valueOf(id))
        .setIssuedAt(issuedAt)
        .setExpiration(expirationAt)
        // TODO refactor without the static method
        .setId(UUID.randomUUID().toString())
        .signWith(jwtConfiguration.getEncodedKey())
        .compact();
  }
}

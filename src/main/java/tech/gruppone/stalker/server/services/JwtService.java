package tech.gruppone.stalker.server.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
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

  public Jws<Claims> getJWTString(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfiguration.getEncodedKey())
        .build()
        .parseClaimsJws(token);
  }

  public boolean isTokenValid(String token) {
    try {
      getJWTString(token);
    } catch (IllegalArgumentException e) {
      return false;
    } catch (JwtException e) {
      return false;
    }

    return true;
  }

  public int getUserId(String token) {
    return Integer.parseInt(getJWTString(token).getBody().getSubject());
  }

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

  public String createAnonymousToken() {
    Date issuedAt = Date.from(Instant.now(clock));
    Date expirationAt =
        new Date(
            issuedAt.getTime() + Long.parseLong(jwtConfiguration.getExpirationTime()) * 1000000);

    // no jti field because the spec says it's optional
    return Jwts.builder()
        .setSubject(UUID.randomUUID().toString())
        .setIssuedAt(issuedAt)
        .setExpiration(expirationAt)
        .claim("anonymous", true)
        .signWith(jwtConfiguration.getEncodedKey())
        .compact();
  }
}

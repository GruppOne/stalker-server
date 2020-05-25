package tech.gruppone.stalker.server.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.gruppone.stalker.server.configuration.JwtConfiguration;

@Service
public class JwtService {

  @Autowired JwtConfiguration jwtConfiguration;

  // TODO should probably move the following methods to a separate class called JwtTokenService

  public Claims getJWTString(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfiguration.getEncodedKey())
        .build().parseClaimsJws(token).getBody();
  }

  public Boolean isTokenSigned(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfiguration.getEncodedKey())
        .build()
        .isSigned(token);
  }

  public String getUserId(String token) {
    return getJWTString(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    Date date = new Date();
    return getJWTString(token).getExpiration().before(date);
  }

  public String createToken(Long id) {
    Date issuedAt = new Date();
    Date expirationAt =
        new Date(
            issuedAt.getTime() + Long.parseLong(jwtConfiguration.getExpirationTime())* 1000) ;
    return Jwts.builder()
        .setSubject(String.valueOf(id))
        .setIssuedAt(issuedAt)
        .setExpiration(expirationAt)
        .setId(UUID.randomUUID().toString())
        .signWith(jwtConfiguration.getEncodedKey())
        .compact();
  }
}

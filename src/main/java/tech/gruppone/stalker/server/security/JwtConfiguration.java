package tech.gruppone.stalker.server.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class JwtConfiguration{

  @Value("${spring.jwt.secret}")
  private String secret;

  @Value("${spring.jwt.expirationtime}")
  private String expirationTime;

  private Key getEncodedKey(){
    byte[] keyBytes = Decoders.BASE64.decode(this.secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims getJWTString(String token){
    return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().parseClaimsJws(token).getBody();
  }

  public Boolean isTokenSigned(String token){
    return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().isSigned(token);
  }

  public String getUsername(String token){
    return getJWTString(token).getSubject();
  }

  public String getId(String token){
    return getJWTString(token).getId();
  }


  public Date getExpirationDate(String token){
    return getJWTString(token).getExpiration();
  }

  public boolean isTokenExpired(String token){
    Date date = new Date();
    return getExpirationDate(token).before(date);
  }


  public String createToken(Long id){
    Date issuedAt = new Date();
    Date expirationAt= new Date(issuedAt.getTime() + Long.parseLong(expirationTime));
    return Jwts.builder().setSubject(String.valueOf(id)).setIssuedAt(issuedAt).setExpiration(expirationAt).setId(UUID.randomUUID().toString()).signWith(getEncodedKey()).compact();
  }

  }



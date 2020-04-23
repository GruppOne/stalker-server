package tech.gruppone.stalker.server.security;


import com.sun.mail.imap.protocol.BASE64MailboxEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil{

   @Value("${springsecurity.jwt.key}")
   private String key;

   @Value("${springsecurity.jwt.expirationtime}")
   private String expirationTime;

   public Claims getJWTString(String token){
      return Jwts.parserBuilder().setSigningKey(BASE64MailboxEncoder.encode(key)).build().parseClaimsJws(token).getBody();
   }

   public String getUsername(String token){
     return getJWTString(token).getSubject();
   }

   public Date getExpirationDate(String token){
     return getJWTString(token).getExpiration();
   }

   public boolean isTokenExpired(String token){
      Date date = new Date();
      return getExpirationDate(token).before(date);
   }

   // this function creates a jwt token. It is not complete
   public String createToken(String username, Map <String, Object> claims){
     Date x = new Date();
     return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(x).setExpiration(x).signWith(Keys.hmacShaKeyFor(key.getBytes())).compact();
   }

   public boolean isTokenStillValid(String token){
     return !isTokenExpired(token);
   }
}

package tech.gruppone.stalker.server.security;


import com.sun.mail.imap.protocol.BASE64MailboxEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDate;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

   @Value("${springSecurity.jwt.key}")
   private String key;

   @Value("${springSecurity.jwt.expirationTime}")
   private String expirationTime;

   public Claims getJWTString(String token){
      return Jwts.parserBuilder().setSigningKey(BASE64MailboxEncoder.encode(key)).build().parseClaimsJws(token).getBody();
   }

   public String getUsername(String token){
     return getJWTString(token).getSubject();
   }

   public String getExpirationDate(String token){
     return getJWTString(token).getExpiration().toString();
   }

   // probably not correct.
   public boolean isTokenExpired(String token){
     return LocalDate.now().toString() == getExpirationDate(token);
   }

   // this function creates a jwt token. It is not complete
   public String createToken(String username, Map <String, Object> claims){

     SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
     return Jwts.builder().setSubject(username).setClaims(claims).signWith(secretKey).compact();
   }
}

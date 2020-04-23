package tech.gruppone.stalker.server.security;


import com.sun.mail.imap.protocol.BASE64MailboxEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class JwtUtil{

   @Value("${springsecurity.jwt.secret}")
   private String secret;

   @Value("${springsecurity.jwt.expirationtime}")
   private String expirationTime;

   private Key getEncodedKey(){
     byte[] keyBytes = Decoders.BASE64.decode(this.secret);
     return Keys.hmacShaKeyFor(keyBytes);
   }

   public Claims getJWTString(String token){
      return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().parseClaimsJws(token).getBody();
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
     Date x = new Date(); // dummy date
     return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(x).setExpiration(x).signWith(getEncodedKey()).compact();
   }

   public boolean isTokenStillValid(String token){
     return !isTokenExpired(token);
   }
}

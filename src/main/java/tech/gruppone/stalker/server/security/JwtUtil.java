package tech.gruppone.stalker.server.security;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import tech.gruppone.stalker.server.model.UserRoles;

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

   public Boolean isTokenSigned(String token){

     return Jwts.parserBuilder().setSigningKey(getEncodedKey()).build().isSigned(token);
   }


   public String getUsername(String token){
     return getJWTString(token).getSubject();
   }

   public String getId(String token){ return getJWTString(token).getId();}

   public Date getExpirationDate(String token){
     return getJWTString(token).getExpiration();
   }

   public boolean isTokenExpired(String token){
      Date date = new Date();
      return getExpirationDate(token).before(date);
   }


   public String createToken(Long id, String username, Map <String, List<UserRoles>> claims){

     Date issuedAt = new Date(); // dummy date
     Date expirationAt= new Date(issuedAt.getTime() + Long.parseLong(expirationTime));
     return Jwts.builder().setClaims(claims).setId(Long.toString(id)).setSubject(username).
       setIssuedAt(issuedAt).setExpiration(expirationAt).signWith(getEncodedKey()).compact();
   }


   public List<UserRoles> parseUserRoles(String token) throws IOException {
     Claims claims = getJWTString(token);
     Object organizations = claims.get("organizationRoles");
     ByteArrayOutputStream out = new ByteArrayOutputStream();
     ObjectMapper mapper = new ObjectMapper();
     mapper.writeValue(out, organizations);
     byte[] data = out.toByteArray();
     String str = new String(data);
     List<UserRoles> list = mapper.readValue(str, new TypeReference<List<UserRoles>>() { });
     return list;

  }
}

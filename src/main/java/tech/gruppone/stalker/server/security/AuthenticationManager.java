package tech.gruppone.stalker.server.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  @Autowired
  JwtUtil jwtToken;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    if (jwtToken.isTokenExpired(token)) {
      return Mono.empty();
    } else {
      UsernamePasswordAuthenticationToken accessToken = new UsernamePasswordAuthenticationToken(jwtToken.getUsername(
        token), null, null);
      return Mono.just(accessToken);
    }
  }
}

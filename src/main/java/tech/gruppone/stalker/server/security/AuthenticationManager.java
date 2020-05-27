package tech.gruppone.stalker.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.UnauthorizedException;
import tech.gruppone.stalker.server.services.JwtService;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  @Autowired JwtService jwtService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getName();

    // int id=Integer.parse(jwtService.getUserId(token));

    if (token == null || !jwtService.isTokenValid(token)) {
      return Mono.error(new UnauthorizedException());
    }

    authentication.setAuthenticated(true);

    return Mono.just(authentication);
  }
}

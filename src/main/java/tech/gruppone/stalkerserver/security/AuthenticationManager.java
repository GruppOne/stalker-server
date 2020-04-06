/*package tech.gruppone.stalkerserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {


  @Value("${my.api.key}")
  String api_key;

  @Override
  public Mono<Authentication> authenticate(
    Authentication authentication) {
    String principal = (String) authentication.getPrincipal();
    if (!api_key.equals(principal))
    {
      throw new BadCredentialsException("The API key is not correct");
    }
    authentication.setAuthenticated(true);
    return  Mono. just(authentication);
  }
}*/

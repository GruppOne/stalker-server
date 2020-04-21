/*package tech.gruppone.stalkerserver.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String principal = (String) authentication.getPrincipal();
    if (!api_value.equals(principal))
    {
      throw new BadCredentialsException("The API key is not correct");
    }
    authentication.setAuthenticated(true);
    return authentication;
    }
}*/

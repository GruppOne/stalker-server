package tech.gruppone.stalker.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

  @Autowired private AuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    throw new UnsupportedOperationException("unsupported operation");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
      return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
    }
    return Mono.empty();
  }
}

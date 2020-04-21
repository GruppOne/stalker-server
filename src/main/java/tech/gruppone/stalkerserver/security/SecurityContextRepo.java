/*package tech.gruppone.stalkerserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class SecurityContextRepo  implements ServerSecurityContextRepository {

  @Value("${my.api.value}")
  private String api_value;

  @Autowired
  private Authentication authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange,
    SecurityContext context) {
    throw new UnsupportedOperationException("not a supported operation")
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    ServerHttpRequest request= exchange.getRequest();
    String Header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if(Header.equals(api_value)){
      return this.authenticationManager.
    }

  }
}*/

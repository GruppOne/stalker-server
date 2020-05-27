package tech.gruppone.stalker.server.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestConverter implements ServerAuthenticationConverter {

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String authHeader = request.getHeaders().getFirst("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer")) {
      return Mono.just(new BearerTokenAuthenticationToken(authHeader.substring(7)));
    }

    return Mono.empty();
  }
}

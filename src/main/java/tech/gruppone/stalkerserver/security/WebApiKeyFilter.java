package tech.gruppone.stalkerserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class WebApiKeyFilter implements WebFilter {

  @Value("${my.api.key:key}")
  private String api_key;

  @Value("${my.api.value:value}")
  private String api_value;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request= exchange.getRequest();
    String auth= request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if(header.startsheader.get(api_key).equals(api_value)){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    else{
      return chain.filter(exchange);
    }

  }
}

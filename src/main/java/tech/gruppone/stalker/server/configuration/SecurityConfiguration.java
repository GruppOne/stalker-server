package tech.gruppone.stalker.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@PropertySource("classpath:application.properties")
class SecurityConfiguration {
  @Bean
  protected SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
    return http
      //disable csrf
      .csrf().disable()
      //disable login form
      .formLogin().disable()
      //disable logout form
      .logout().disable()
      // disable basic authentication
      .httpBasic().disable()
      // setup permissions
      .authorizeExchange()
      //allow everyone to login
      .pathMatchers(HttpMethod.POST, "/user/login").permitAll()
      //allow everyone to signup
      .pathMatchers(HttpMethod.POST, "/users").permitAll()
      //allow everyone to read version
      .pathMatchers(HttpMethod.GET, "/version").permitAll()
      //require authentication for all other endpoints
      .anyExchange().authenticated()
      // complete the filter chain
      .and().build();
  }
}

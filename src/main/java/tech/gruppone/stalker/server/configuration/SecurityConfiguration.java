package tech.gruppone.stalker.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import tech.gruppone.stalker.server.security.AuthenticationManager;
import tech.gruppone.stalker.server.security.RequestConverter;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@PropertySource("classpath:application.properties")
class SecurityConfiguration {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired RequestConverter requestConverter;

  @Bean
  protected SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
    return http
        // disable csrf
        .csrf()
        .disable()
        // disable login form
        .formLogin()
        .disable()
        // disable logout form
        .logout()
        .disable()
        // disable basic authentication
        .httpBasic()
        .disable()
        // configure oauth2 resource server auth
        .oauth2ResourceServer()
        // enable jwt auth
        .jwt()
        // setup authentication manager
        .authenticationManager(authenticationManager)
        // go back to oauth2 settings
        .and()
        // setup converter
        .bearerTokenConverter(requestConverter)
        // go back to configuring the filter chain
        .and()
        // setup authentication manager
        // .authenticationManager(authenticationManager)
        // setup security context repository
        // .securityContextRepository(securityContextRepository)
        // setup permissions
        .authorizeExchange()
        // allow everyone to login
        .pathMatchers(HttpMethod.POST, "/user/login")
        .permitAll()
        // allow everyone to signup
        .pathMatchers(HttpMethod.POST, "/users")
        .permitAll()
        // allow everyone to read version
        .pathMatchers(HttpMethod.GET, "/version")
        .permitAll()
        // require authentication for all other endpoints
        .anyExchange()
        .authenticated()
        // complete the filter chain
        .and()
        .build();
  }
}

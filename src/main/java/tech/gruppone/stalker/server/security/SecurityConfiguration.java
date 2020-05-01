package tech.gruppone.stalker.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfiguration {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  SecurityContextRepository securityContextRepository;

  @Bean
  protected SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
      http.
      csrf().disable().  // disable csrf for now  --> if you want to enable use (.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).)
      formLogin().disable().// disable form login
       logout().disable();
        http.httpBasic().disable();// disable basic authentication

      http.authenticationManager(this.authenticationManager); // set the authentication manager
      http.securityContextRepository(this.securityContextRepository); // set the context repository

      http.authorizeExchange().
       pathMatchers(HttpMethod.POST, "/login").permitAll().
        pathMatchers(HttpMethod.GET, "/users/roles/{username}").permitAll().
       pathMatchers(HttpMethod.POST, "/users/registration").permitAll();// disable security for registration
       http.authorizeExchange().anyExchange().authenticated(); //any other request must be authenticated

      return http.build();
  }



}

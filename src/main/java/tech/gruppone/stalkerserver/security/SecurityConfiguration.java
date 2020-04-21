package com.example.Stalkerserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebFluxSecurity
@Configuration
@PropertySource("classpath:application.properties")
public class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  /*@Value("${my.api.key}")
  private String api_key;

  @Value("${my.api.value}")
  private String api_value;

  @Override
  protected void configure(ServerHttpSecurity http) throws Exception{
    ApiKeyAuthenticationFilter filter  = new ApiKeyAuthenticationFilter(api_key);
    filter.setAuthenticationManager(new AuthenticationManager() {

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
    });
    http.
      antMatcher("/**").
      csrf().disable().                        //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).
      sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
      and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
  }*/



}

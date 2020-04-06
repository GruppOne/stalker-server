// package com.example.Stalkerserver.security;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableWebFluxSecurity
// @Configuration
// @PropertySource("classpath:application.properties")
// public class SecurityConfiguration {


//   @Value("${my.api.key}")
//   private String api_key;

//   @Value("${my.api.value}")
//   private String api_value;

//   @Override
//   protected void configure(ServerHttpSecurity http) throws Exception{
//     ApiKeyAuthenticationFilter filter  = new ApiKeyAuthenticationFilter(api_key);
//     filter.setAuthenticationManager(new AuthenticationManager() {

//       @Override
//       public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         String principal = (String) authentication.getPrincipal();
//         if (!api_value.equals(principal))
//         {
//           throw new BadCredentialsException("The API key is not correct");
//         }
//         authentication.setAuthenticated(true);
//         return authentication;
//       }
//     });
//     http.
//       antMatcher("/**").
//       csrf().disable().                        //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).
//       sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//       and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
//   }



// }

// package tech.gruppone.stalkerserver.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.ReactiveAuthenticationManager;
// import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.context.SecurityContextRepository;
// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.security.web.server.context.ServerSecurityContextRepository;
// import org.springframework.web.reactive.config.EnableWebFlux;
// import reactor.core.publisher.Mono;

// @Configuration
// @PropertySource("classpath:application.properties")
// public class securityConfiguration {


//     //@Value("${my.api.value}")
//     String api_key= "xD";


//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity  http){
//       http.httpBasic();
//       http.formLogin().disable();
//       http.csrf().disable();
//       http.authenticationManager(new ReactiveAuthenticationManager() {
//         @Override
//         public Mono<Authentication> authenticate(
//           Authentication authentication) {
//           String principal = (String) authentication.getPrincipal();
//           if (!api_key.equals(principal))
//           {
//             throw new BadCredentialsException("The API key is not correct");
//           }
//           authentication.setAuthenticated(true);
//           return  Mono. just(authentication);
//         }
//       });
//       http.authorizeExchange().anyExchange().authenticated();

//       return http.build();

//     }
// }

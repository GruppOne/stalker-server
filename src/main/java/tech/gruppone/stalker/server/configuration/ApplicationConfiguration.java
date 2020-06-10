package tech.gruppone.stalker.server.configuration;

import java.time.Clock;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
@lombok.Value
@NonFinal
public class ApplicationConfiguration implements WebFluxConfigurer {

  String version;

  public ApplicationConfiguration(@Value("${spring.application.version}") String version) {
    this.version = version;
  }

  // decouple system clock for testing purposes
  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry
        .addMapping("/**")
        // .allowedOrigins("https://gruppone.tech")
        // .allowedOrigins("http://localhost:4200")
        // temporarily allow all origins
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}

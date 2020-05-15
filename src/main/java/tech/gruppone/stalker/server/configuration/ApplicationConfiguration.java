package tech.gruppone.stalker.server.configuration;

import lombok.NonNull;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
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

  public ApplicationConfiguration(@NonNull @Value("${spring.application.version}") String version) {
    this.version = version;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry
        .addMapping("/**")
        // .allowedOrigins("https://gruppone.tech")
        .allowedOrigins("http://localhost:4200")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}

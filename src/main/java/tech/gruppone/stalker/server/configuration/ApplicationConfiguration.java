package tech.gruppone.stalker.server.configuration;

import lombok.NonNull;
import lombok.experimental.NonFinal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@lombok.Value
@NonFinal
public class ApplicationConfiguration {

  String version;

  public ApplicationConfiguration(@NonNull @Value("${spring.application.version}") String version) {
    this.version = version;
  }
}

package tech.gruppone.stalkerserver.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationConfiguration {

  private final String version;

  public ApplicationConfiguration(@Value("${spring.application.version}") String version) {
    this.version = version;
  }

}

package tech.gruppone.stalkerserver.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Configuration
@PropertySource("classpath:application.properties")
@RestController
@Getter
public class ApplicationConfiguration {

  @NonNull
  private final String version;

  public ApplicationConfiguration(@Value("${spring.application.version}") String version) {
    this.version = version;
  }

  @GetMapping("/version")
  public Mono<String> currentServerVersion() {

    return Mono.just(version);
  }
}

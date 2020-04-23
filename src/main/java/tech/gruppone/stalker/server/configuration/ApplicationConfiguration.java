package tech.gruppone.stalker.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.NonNull;
import reactor.core.publisher.Mono;

@Configuration
@PropertySource("classpath:application.properties")
// FIXME this class should NOT be a controller
@RestController
public class ApplicationConfiguration {

  private final String version;

  public ApplicationConfiguration(@NonNull @Value("${spring.application.version}") String version) {
    this.version = version;
  }

  @GetMapping("/version")
  public Mono<String> currentServerVersion() {

    // FIXME should return an application/json made like {"version":...}
    return Mono.just(version);
  }

}

package tech.gruppone.stalker.server.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

// TODO refactor using @configurationproperties for typed config values?
@Configuration
@PropertySource("classpath:application.properties")
@Getter
@RestController
public class ApplicationConfiguration {

  private final String version;

  public ApplicationConfiguration(@NonNull @Value("${spring.application.version}") String version) {
    this.version = version;
  }

  @GetMapping(value = {"/", "/version"})
  public Mono<String> currentServerVersion() {

    // FIXME should not be done like this
    final String versionObject = "{\"version\":\"" + version + "\"}";
    return Mono.just(versionObject);
  }

  // TODO inner class named versioninfo. move restcontroller there
}

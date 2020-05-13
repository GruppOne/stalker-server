package tech.gruppone.stalker.server.controllers;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.ApplicationConfiguration;

@RestController
@RequestMapping("/version")
@Value
public class VersionController {

  ApplicationConfiguration applicationConfiguration;

  @GetMapping
  public Mono<VersionResponseBody> getVersion() {
    return Mono.just(applicationConfiguration.getVersion()).map(VersionResponseBody::new);
  }

  @Value
  private static class VersionResponseBody {
    String version;
  }
}

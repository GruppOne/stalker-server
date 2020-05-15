package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.ApplicationConfiguration;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/version")
public class VersionController {

  ApplicationConfiguration applicationConfiguration;

  @GetMapping
  public Mono<GetVersionResponseBody> getVersion() {
    return Mono.just(applicationConfiguration.getVersion()).map(GetVersionResponseBody::new);
  }

  @Value
  private static class GetVersionResponseBody {
    String version;
  }
}

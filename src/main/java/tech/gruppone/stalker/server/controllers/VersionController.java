package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.configuration.ApplicationConfiguration;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class VersionController {

  ApplicationConfiguration applicationConfiguration;

  @GetMapping("/version")
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetVersionResponseBody> getVersion() {
    return Mono.just(applicationConfiguration.getVersion()).map(GetVersionResponseBody::new);
  }

  @Value
  private static class GetVersionResponseBody {
    String version;
  }
}

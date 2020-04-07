package tech.gruppone.stalkerserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalkerserver.configuration.ApplicationConfiguration;

@SpringBootApplication
@EnableWebFlux
@RestController
public class Application {

  // TODO print app name and version
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

  }


  @GetMapping(value = {"/", "/version"})
  public Mono<String> currentServerVersion(@Autowired ApplicationConfiguration applicationConfiguration) {

    return Mono.just(applicationConfiguration.getVersion());
  }

}

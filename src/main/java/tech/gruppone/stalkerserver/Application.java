package tech.gruppone.stalkerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class Application {

  // TODO print app name and version
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

  }
}

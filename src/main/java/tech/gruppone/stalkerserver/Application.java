package tech.gruppone.stalkerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;


//@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class })
@EnableWebFlux
@SpringBootApplication
@RestController
public class Application {

  // TODO print app name and version
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

  }
}

package tech.gruppone.stalkerserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO test that configuration works as expected
//TODO should eventually use configuration annotation
//FIXME should not be a RestController
//@Configuration
@PropertySource("classpath:application.properties")
@RestController
public class ApplicationConfiguration {

  @GetMapping("/version")
  public String currentServerVersion(@Value("${spring.application.version}") String version) {
    return version;
  }
}

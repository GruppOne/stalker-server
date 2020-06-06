package tech.gruppone.stalker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

// XXX we use the annotation @Log4j2 for logging purposes.
@SpringBootApplication
@EnableR2dbcRepositories
// TODO try to use @EnableTransactionManagement
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}

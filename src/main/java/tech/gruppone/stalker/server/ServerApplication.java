package tech.gruppone.stalker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

// TODO find a way to return 501 NOT IMPLEMENTED
// TODO decide which logger we use. Should be used with the appropriate lombok @log annotation( e.g. @Log4j)
// TODO find an easy way to wrap values inside a valid json
@SpringBootApplication
@EnableR2dbcRepositories
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);

  }

}

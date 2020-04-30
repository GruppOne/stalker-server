package tech.gruppone.stalker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

// import lombok.extern.slf4j.Slf4j;

// TODO find a way to return 501 NOT IMPLEMENTED
// TODO find an easy way to wrap values inside a valid json
// @Slf4j
@SpringBootApplication
@EnableR2dbcRepositories
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);

  }

}

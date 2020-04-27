package tech.gruppone.stalker.server;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

// TODO find a way to return 501 NOT IMPLEMENTED
// TODO decide which logger we use. Should be used with the appropriate lombok @log annotation. will probably be @slf4j
// TODO find an easy way to wrap values inside a valid json
@SpringBootApplication
@EnableR2dbcRepositories
public class ServerApplication {

  @Autowired
  private ObjectMapper objectMapper;

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);

  }

  @PostConstruct
  public void setUp() {
    objectMapper.registerModule(new JavaTimeModule());
  }

}

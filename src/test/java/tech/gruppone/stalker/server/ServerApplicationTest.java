package tech.gruppone.stalker.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.gruppone.stalker.server.configuration.ApplicationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerApplicationTest {

  @Autowired ApplicationConfiguration applicationConfiguration;

  @Test
  void contextLoads() {
    assertThat(applicationConfiguration).isNotNull();
    assertThat(applicationConfiguration.getVersion()).isNotNull();
  }
}

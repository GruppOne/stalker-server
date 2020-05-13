package tech.gruppone.stalker.server.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
// @PropertySource("classpath:local.properties")
@Getter
public class InfluxDbConfiguration {

  // TODO use constructor value injection
  @NonNull
  @Value("${spring.influx.url}")
  private String url;

  @NonNull
  @Value("${spring.influx.database}")
  private String databaseName;

  @NonNull
  @Value("${spring.influx.user}")
  private String userName;

  @NonNull
  @Value("${spring.influx.password}")
  private String password;
}

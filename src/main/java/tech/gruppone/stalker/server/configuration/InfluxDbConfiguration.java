package tech.gruppone.stalker.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;
import lombok.NonNull;

@Configuration
@PropertySource("classpath:application.properties")
// @PropertySource("classpath:local.properties")
@Getter
public class InfluxDbConfiguration {

  // TODO use constructor value injection
  @NonNull
  @Value("${influxdb.url}")
 private String url;

  @NonNull
  @Value("${influxdb.database}")
 private String databaseName;

  @NonNull
  @Value("${influxdb.user}")
 private String userName;

  @NonNull
  @Value("${influxdb.password}")
 private String password;

}

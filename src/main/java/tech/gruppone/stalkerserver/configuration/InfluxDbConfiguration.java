package tech.gruppone.stalkerserver.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//TODO refactor using @configurationproperties for typed config values
@Configuration
@PropertySource("classpath:local.properties")
@Getter
public class InfluxDbConfiguration {

  @NonNull
  @Value("${influxdb.url}")
  String url;
  @NonNull
  @Value("${influxdb.database}")
  String databaseName;
  @NonNull
  @Value("${influxdb.user}")
  String userName;
  @NonNull
  @Value("${influxdb.password}")
  String password;

}

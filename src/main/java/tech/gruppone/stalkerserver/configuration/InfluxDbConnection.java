package tech.gruppone.stalkerserver.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

// TODO REMEMBER TO USE BEANS/IOC AND NOT NEW!
//TODO we probably need this for the ioc to work
// @Configuration
@PropertySource("classpath:local.properties")
public class InfluxDbConnection {

  // this annotation AUTOMATICALLY CREATES A getVariable method!
  @Getter
  private final String url = "http://localhost:8086";

  // TODO move explicit properties to application.properties
  @Getter
  private final String databaseName = "stalker-tsdb";
  @Getter
  private final String userName = "root";
  @Getter
  private final String password;

  InfluxDbConnection(@NonNull @Value("${spring.influxdb.password}") String password) {
    this.password = password;
  }

}

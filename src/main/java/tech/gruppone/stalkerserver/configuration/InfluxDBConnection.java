package tech.gruppone.stalkerserver.configuration;

import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//TODO REMEMBER TO USE BEANS/IOC AND NOT NEW!
//TODO we probably need this for the ioc to work
@Configuration
@PropertySource("classpath:local.properties")
public class InfluxDBConnection {

  // this annotation AUTOMATICALLY CREATES A getVariable method!
  @Getter(AccessLevel.PUBLIC)
  private final String url = "http://localhost:8086";
  @Getter(AccessLevel.PUBLIC)
  private final String userName = "root";
  @Getter(AccessLevel.PRIVATE)
  private final String password;
  @Getter(AccessLevel.PUBLIC)
  private final String databaseName = "stalker_tsdb";

  public InfluxDBConnection(@NonNull @Value("${spring.influxdb.password}") String password) {
    this.password = password;
  }

  @Bean
  public InfluxDB influxDB(@Value("${spring.influxdb.password}") String password){
    InfluxDB influxDB = InfluxDBFactory.connect(url, userName, password).setDatabase(databaseName);
    try {
      influxDB.setDatabase(databaseName).enableBatch(100,2000, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      influxDB.setRetentionPolicy("autogen");
    }

    influxDB.enableGzip();
    influxDB.setLogLevel(LogLevel.FULL);
    return influxDB;
  }

}

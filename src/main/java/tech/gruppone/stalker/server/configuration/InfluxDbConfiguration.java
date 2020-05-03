package tech.gruppone.stalker.server.configuration;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.NonNull;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@PropertySource("classpath:application.properties")
@lombok.Value
@NonFinal
public class InfluxDbConfiguration extends InfluxDbAutoConfiguration {

  String url;
  String database;
  String retentionPolicy;
  String username;
  String password;

  public InfluxDbConfiguration(@NonNull @Value("${spring.influx.url}") String url,
      @NonNull @Value("${spring.influx.database}") String database,
      @NonNull @Value("${spring.influx.retention}") String retentionPolicy,
      @NonNull @Value("${spring.influx.user}") String userName,
      @NonNull @Value("${spring.influx.password}") String password) {
    this.url = url;
    this.database = database;
    this.retentionPolicy = retentionPolicy;
    this.username = userName;
    this.password = password;
  }

  // TODO handle errors when establishing the connection
  @Bean(destroyMethod = "close")
  public InfluxDB getInfluxDBConnection() {
    log.info("creating connection to InfluxDB {} on db {}", url, database);

    final var influxDB = InfluxDBFactory.connect(url, username, password).setDatabase(database)
        .setRetentionPolicy(retentionPolicy).enableGzip().setLogLevel(LogLevel.BASIC)
        .enableBatch(BatchOptions.DEFAULTS);

    // XXX careful! this operation is blocking.
    var queryResult = influxDB.query(new Query("SHOW DATABASES"));
    var result = queryResult.getResults();

    log.info(result);

    return influxDB;
  }

  @Bean
  public InfluxDBMapper getInfluxDBMapper() {
    return new InfluxDBMapper(getInfluxDBConnection());
  }

}

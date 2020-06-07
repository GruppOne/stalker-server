package tech.gruppone.stalker.server.configuration;

import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

@Log4j2
@Configuration
@PropertySource("classpath:application.properties")
@lombok.Value
@NonFinal
public class InfluxDbConfiguration {

  String url;
  String database;
  String retentionPolicy;
  String username;
  String password;

  public InfluxDbConfiguration(
      @NonNull @Value("${influxdb.url}") String url,
      @NonNull @Value("${influxdb.database}") String database,
      @NonNull @Value("${influxdb.retention}") String retentionPolicy,
      @NonNull @Value("${influxdb.user}") String userName,
      @NonNull @Value("${influxdb.password}") String password) {
    this.url = url;
    this.database = database;
    this.retentionPolicy = retentionPolicy;
    this.username = userName;
    this.password = password;
  }

  // FIXME fails silently when connection cannot be established
  // TODO handle errors when establishing the connection. maybe add spring retry?
  @Bean(name = "influxDbConnection", destroyMethod = "close")
  public InfluxDB getInfluxDBConnection() {
    log.info("creating connection to InfluxDB {} on db {}", url, database);

    final OkHttpClient.Builder httpClientBuilder =
        new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(false);

    final InfluxDB influxDB =
        InfluxDBFactory.connect(url, username, password, httpClientBuilder)
            .setDatabase(database)
            .setRetentionPolicy(retentionPolicy)
            .enableGzip()
            .setLogLevel(LogLevel.FULL)
            .enableBatch(BatchOptions.DEFAULTS.precision(TimeUnit.MILLISECONDS));

    log.info(influxDB);
    // TODO how to catch errors here?
    // influxDB.ping();

    return influxDB;
  }

  @Bean
  @DependsOn("influxDbConnection")
  public InfluxDBMapper getInfluxDBMapper(InfluxDB influxDB) {
    return new InfluxDBMapper(influxDB);
  }
}

package tech.gruppone.stalker.server.configuration;

import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Log4j2
@Configuration
@PropertySource("classpath:application.properties")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InfluxDbConfiguration {

  @Getter String database;
  @Getter String retentionPolicy;

  @Getter String measurement = "complete_log";

  // XXX fails silently when influxdb connection cannot be established
  public InfluxDbConfiguration(
      @Value("${influxdb.database}") String database,
      @Value("${influxdb.retention}") String retentionPolicy) {
    this.database = database;
    this.retentionPolicy = retentionPolicy;
  }

  @Bean
  InfluxDbOkHttpClientBuilderProvider influxDbOkHttpClientBuilderProvider() {
    log.info("configuring InfluxDB OkHttpClient client");

    return () ->
        new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS);
  }

  @Bean
  public InfluxDBMapper influxDBMapper(InfluxDB influxDb) {
    // this isn't the most appropriate place to configure this stuff
    log.info("configuring InfluxDB connection");
    influxDb
        .setDatabase(database)
        .setRetentionPolicy(retentionPolicy)
        // .enableGzip()
        .enableBatch()
        // .enableBatch(BatchOptions.DEFAULTS.precision(TimeUnit.MILLISECONDS))
        .setLogLevel(LogLevel.FULL);

    log.info("instantiating InfluxDBMapper bean");
    return new InfluxDBMapper(influxDb);
  }
}

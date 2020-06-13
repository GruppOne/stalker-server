package tech.gruppone.stalker.server.configuration;

import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.influxdb.BatchOptions;
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
  @Getter String measurement;
  @Getter String defaultRetentionPolicy;
  @Getter String infiniteRetentionPolicy;

  public InfluxDbConfiguration(
      @Value("${influxdb.database}") final String database,
      @Value("${influxdb.measurement}") final String measurement,
      @Value("${influxdb.default-retention}") final String defaultRetentionPolicy,
      @Value("${influxdb.infinite-retention}") final String infiniteRetentionPolicy) {

    this.database = database;
    this.measurement = measurement;
    this.defaultRetentionPolicy = defaultRetentionPolicy;
    this.infiniteRetentionPolicy = infiniteRetentionPolicy;
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
  public InfluxDBMapper influxDBMapper(final InfluxDB influxDb) {
    // this isn't the most appropriate place to configure this stuff
    log.info("configuring InfluxDB connection");
    influxDb
        .setDatabase(database)
        .setRetentionPolicy(defaultRetentionPolicy)
        .enableGzip()
        // .enableBatch()
        .enableBatch(BatchOptions.DEFAULTS.precision(TimeUnit.MILLISECONDS))
        .setLogLevel(LogLevel.FULL);

    log.info("instantiating InfluxDBMapper bean");
    return new InfluxDBMapper(influxDb);
  }
}

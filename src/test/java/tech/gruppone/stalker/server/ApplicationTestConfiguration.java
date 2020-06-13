package tech.gruppone.stalker.server;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class ApplicationTestConfiguration {
  public static final LocalDateTime FIXED_LOCAL_DATE_TIME =
      LocalDateTime.parse("2020-01-01T01:01:01.01");

  // override system clock with a fixed clock
  @Bean
  @Primary
  public Clock fixedClock() {
    // this is horrifyingly verbose
    // final ZoneId zoneId = ZoneId.of("Europe/Rome");
    final ZoneId zoneId = ZoneId.systemDefault();
    final ZoneOffset zoneOffset = zoneId.getRules().getOffset(FIXED_LOCAL_DATE_TIME);

    return Clock.fixed(FIXED_LOCAL_DATE_TIME.toInstant(zoneOffset), zoneId);
  }
}

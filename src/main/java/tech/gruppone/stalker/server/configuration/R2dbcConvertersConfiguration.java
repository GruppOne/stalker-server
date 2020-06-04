package tech.gruppone.stalker.server.configuration;

import io.r2dbc.spi.ConnectionFactory;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import tech.gruppone.stalker.server.model.db.converters.OrganizationRoleReadConverter;
import tech.gruppone.stalker.server.model.db.converters.OrganizationRoleWriteConverter;

@Log4j2
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class R2dbcConvertersConfiguration extends AbstractR2dbcConfiguration {

  ConnectionFactory connectionFactory;

  // return the unchanged default connection factory
  public ConnectionFactory connectionFactory() {
    log.warn("this ConnectionFactory getter should never actually be invoked.");

    return connectionFactory;
  }

  @Bean
  @Override
  public R2dbcCustomConversions r2dbcCustomConversions() {
    // TODO implement PlacePosition converters
    List<Converter<?, ?>> converterList =
        List.of(new OrganizationRoleReadConverter(), new OrganizationRoleWriteConverter());

    return new R2dbcCustomConversions(getStoreConversions(), converterList);
  }
}

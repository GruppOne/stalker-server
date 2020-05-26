package tech.gruppone.stalker.server.configuration;

import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import tech.gruppone.stalker.server.model.db.converters.OrganizationRoleReadConverter;
import tech.gruppone.stalker.server.model.db.converters.OrganizationRoleWriteConverter;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class R2dbcConvertersConfiguration extends AbstractR2dbcConfiguration {

  ConnectionFactory connectionFactory;

  // return the unchanged default connection factory
  public ConnectionFactory connectionFactory() {
    return connectionFactory;
  }

  @Bean
  @Override
  public R2dbcCustomConversions r2dbcCustomConversions() {
    List<Converter<?, ?>> converterList = new ArrayList<>();
    converterList.add(new OrganizationRoleReadConverter());
    converterList.add(new OrganizationRoleWriteConverter());

    return new R2dbcCustomConversions(getStoreConversions(), converterList);
  }
}

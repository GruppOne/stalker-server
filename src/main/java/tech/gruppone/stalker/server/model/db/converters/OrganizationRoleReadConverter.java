package tech.gruppone.stalker.server.model.db.converters;

import io.r2dbc.spi.Row;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

@ReadingConverter
public class OrganizationRoleReadConverter implements Converter<Row, OrganizationRole> {

  public OrganizationRole convert(Row source) {
    return OrganizationRole.builder()
        .id(source.get("id", Long.class))
        .organizationId(source.get("organizationId", Long.class))
        .userId(source.get("userId", Long.class))
        .administratorType(source.get("administratorType", AdministratorType.class))
        .createdDate(source.get("createdDate", LocalDateTime.class))
        .build();
  }
}

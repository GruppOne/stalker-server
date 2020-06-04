package tech.gruppone.stalker.server.repositories.converters;

import io.r2dbc.spi.Row;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

@ReadingConverter
public class OrganizationRoleDaoReadConverter implements Converter<Row, OrganizationRole> {

  public OrganizationRole convert(Row source) {
    // TODO handle enum in a humane way
    final Integer roleKey = source.get("administratorType", Integer.class);
    final AdministratorType administratorType = AdministratorType.values()[roleKey - 1];

    return OrganizationRole.builder()
        .id(source.get("id", Long.class))
        .organizationId(source.get("organizationId", Long.class))
        .userId(source.get("userId", Long.class))
        .administratorType(administratorType)
        .createdDate(source.get("createdDate", LocalDateTime.class))
        .build();
  }
}

package tech.gruppone.stalker.server.model.db.converters;

import io.r2dbc.spi.Row;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

@ReadingConverter
public class OrganizationRoleReadConverter implements Converter<Row, OrganizationRoleDao> {

  public OrganizationRoleDao convert(Row source) {
    return OrganizationRoleDao.builder()
        .id(source.get("id", Long.class))
        .organizationId(source.get("organizationId", Long.class))
        .userId(source.get("userId", Long.class))
        .administratorType(source.get("administratorType", AdministratorType.class))
        .createdDate(source.get("createdDate", LocalDateTime.class))
        .build();
  }
}

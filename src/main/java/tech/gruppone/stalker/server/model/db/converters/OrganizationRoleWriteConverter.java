package tech.gruppone.stalker.server.model.db.converters;

import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

@WritingConverter
public class OrganizationRoleWriteConverter implements Converter<OrganizationRole, OutboundRow> {

  public OutboundRow convert(final OrganizationRole dao) {
    OutboundRow outboundRow = new OutboundRow();

    outboundRow.put("id", SettableValue.fromOrEmpty(dao.getId(), Long.class));
    outboundRow.put("organizationId", SettableValue.from(dao.getOrganizationId()));
    outboundRow.put("userId", SettableValue.from(dao.getUserId()));
    outboundRow.put(
        "administratorType", SettableValue.from(dao.getAdministratorType().getRoleKey()));
    outboundRow.put(
        "createdDate", SettableValue.fromOrEmpty(dao.getCreatedDate(), LocalDateTime.class));

    return outboundRow;
  }
}

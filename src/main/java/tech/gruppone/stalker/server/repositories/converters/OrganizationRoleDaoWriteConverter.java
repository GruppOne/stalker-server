package tech.gruppone.stalker.server.repositories.converters;

import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;
import tech.gruppone.stalker.server.model.db.OrganizationRole;

@WritingConverter
public class OrganizationRoleDaoWriteConverter implements Converter<OrganizationRole, OutboundRow> {

  public OutboundRow convert(final OrganizationRole organizationRoleDao) {
    OutboundRow outboundRow = new OutboundRow();

    outboundRow.put("id", SettableValue.fromOrEmpty(organizationRoleDao.getId(), Long.class));
    outboundRow.put("organizationId", SettableValue.from(organizationRoleDao.getOrganizationId()));
    outboundRow.put("userId", SettableValue.from(organizationRoleDao.getUserId()));
    outboundRow.put(
        "administratorType",
        SettableValue.from(organizationRoleDao.getAdministratorType().getRoleKey()));
    outboundRow.put(
        "createdDate",
        SettableValue.fromOrEmpty(organizationRoleDao.getCreatedDate(), LocalDateTime.class));

    return outboundRow;
  }
}

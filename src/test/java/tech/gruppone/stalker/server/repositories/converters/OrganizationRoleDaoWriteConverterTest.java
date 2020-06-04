package tech.gruppone.stalker.server.repositories.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

public class OrganizationRoleDaoWriteConverterTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Test
  void testConvert() {
    final long id = 1L;
    final long userId = 1L;
    final long organizationId = 1L;
    final AdministratorType administratorType = AdministratorType.ADMIN;

    final OrganizationRoleDao organizationRoleDao =
        OrganizationRoleDao.builder()
            .id(id)
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(administratorType)
            .createdDate(LOCAL_DATETIME)
            .build();

    final OutboundRow sut = new OrganizationRoleDaoWriteConverter().convert(organizationRoleDao);

    assertThat(sut.get(SqlIdentifier.unquoted("id")).getValue()).isEqualTo(id);
    assertThat(sut.get(SqlIdentifier.unquoted("organizationId")).getValue()).isEqualTo(userId);
    assertThat(sut.get(SqlIdentifier.unquoted("userId")).getValue()).isEqualTo(organizationId);
    assertThat(sut.get(SqlIdentifier.unquoted("administratorType")).getValue())
        .isEqualTo(administratorType.getRoleKey());
    assertThat(sut.get(SqlIdentifier.unquoted("createdDate")).getValue()).isEqualTo(LOCAL_DATETIME);
  }
}

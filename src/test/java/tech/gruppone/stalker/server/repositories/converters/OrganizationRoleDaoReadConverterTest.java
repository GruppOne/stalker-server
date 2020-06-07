package tech.gruppone.stalker.server.repositories.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.r2dbc.spi.Row;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import tech.gruppone.stalker.server.model.AdministratorType;
import tech.gruppone.stalker.server.model.db.OrganizationRoleDao;

class OrganizationRoleDaoReadConverterTest {
  private static final LocalDateTime LOCAL_DATETIME = LocalDateTime.parse("2020-01-01T01:01:01.01");

  @Test
  void testConvert() {
    final long id = 1L;
    final long userId = 1L;
    final long organizationId = 1L;
    final AdministratorType administratorType = AdministratorType.VIEWER;

    final OrganizationRoleDao expectedOrganizationRoleDao =
        OrganizationRoleDao.builder()
            .id(id)
            .userId(userId)
            .organizationId(organizationId)
            .administratorType(administratorType)
            .createdDate(LOCAL_DATETIME)
            .build();

    final Row row = mock(Row.class);

    when(row.get("administratorType", Integer.class)).thenReturn(administratorType.getRoleKey());
    when(row.get("id", Long.class)).thenReturn(id);
    when(row.get("organizationId", Long.class)).thenReturn(userId);
    when(row.get("userId", Long.class)).thenReturn(organizationId);
    when(row.get("createdDate", LocalDateTime.class)).thenReturn(LOCAL_DATETIME);

    final var sut = new OrganizationRoleDaoReadConverter().convert(row);

    assertThat(sut).isEqualTo(expectedOrganizationRoleDao);

    verify(row).get("administratorType", Integer.class);
    verify(row).get("id", Long.class);
    verify(row).get("organizationId", Long.class);
    verify(row).get("userId", Long.class);
    verify(row).get("createdDate", LocalDateTime.class);
  }
}

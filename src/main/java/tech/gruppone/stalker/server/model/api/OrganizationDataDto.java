package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class OrganizationDataDto {

  @NonNull String name;

  @NonNull String description;

  @NonNull @Builder.Default String organizationType = "public";

  LdapConfigurationDto ldapConfiguration;

  Timestamp creationDateTime;
  Timestamp lastChangeDateTime;

  @Builder
  @Value
  public static class LdapConfigurationDto {

    @NonNull String url;

    @NonNull String baseDn;

    @NonNull String bindRdn;

    @NonNull String bindPassword;
  }
}

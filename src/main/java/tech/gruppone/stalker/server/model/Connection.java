package tech.gruppone.stalker.server.model;

import org.springframework.data.annotation.Id;

import lombok.NonNull;

//TODO verify how I can make a union with the model ConnectedOrganization (the same for the interface repository and the controller!)
public class Connection {

  @Id
  @NonNull
  private Long organizationId;
  @NonNull
  private Long userId;

  public Long getOrganizationId() {
    return this.organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}

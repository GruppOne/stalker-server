package tech.gruppone.stalker.server.model.db;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.NonNull;
import lombok.Value;

@Value
public class Organization {
  @Id
  long id;
  @NonNull
  String name;
  @NonNull
  String description;
  @NonNull
  @Column("createdDate")
  LocalDateTime createdDate;
  @NonNull
  @Column("lastModifiedDate")
  LocalDateTime lastModifiedDate;
}

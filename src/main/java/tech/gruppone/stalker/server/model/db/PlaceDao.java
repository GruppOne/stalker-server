package tech.gruppone.stalker.server.model.db;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Value
@Table("Place")
public class PlaceDao {

  @With
  @Id
  @Column("id")
  Long id;

  @NonNull
  @Column("organizationId")
  Long organizationId;

  @NonNull
  @Column("name")
  String name;

  @NonNull
  @Column("color")
  String color;

  @NonNull
  @Column("maxConcurrentUsers")
  Integer maxConcurrentUsers;

  @NonNull
  @Column("address")
  String address;

  @NonNull
  @Column("city")
  String city;

  @NonNull
  @Column("zipcode")
  String zipcode;

  @NonNull
  @Column("state")
  String state;
}

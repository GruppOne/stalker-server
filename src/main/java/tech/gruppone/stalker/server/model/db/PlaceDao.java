package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Value
// no @table here because this corresponds to tables Place and PlaceData joined on id
public class PlaceDao {

  @Id
  @Column("id")
  long id;

  @Column("organizationId")
  long organizationId;

  @NonNull
  @Column("name")
  String name;

  @NonNull
  @Column("positionGeoJson")
  String rawPositionJson;

  // we use a single DAO for tables Place and PlaceData
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

package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("Place")
public class PlaceDataDao {

  @Id
  @Column("id")
  long id;

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

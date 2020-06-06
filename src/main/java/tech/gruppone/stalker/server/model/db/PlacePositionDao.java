package tech.gruppone.stalker.server.model.db;

import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("PlacePosition")
public class PlacePositionDao {

  // non null because this PlacePosition's place needs to already exist when inserting
  @NonNull
  @Id
  @Column("id")
  Long id;

  @NonNull
  @Column("positionGeoJson")
  String rawPositionJson;
}

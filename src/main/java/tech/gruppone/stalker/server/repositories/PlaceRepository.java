package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.Place;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdPlacePlaceIdresponse;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdPlacesResponse;

public interface PlaceRepository extends ReactiveCrudRepository<Place,Long> {

  @Query("SELECT p.name, CONVERT(ST_AsText(p.position), CHAR) AS position, pd.address, pd.city, pd.state, pd.zipcode FROM Place p, PlaceData pd WHERE p.organizationId = :organizationId AND p.id = pd.id")
  public Mono<GetOrganizationOrganizationIdPlacesResponse> findAllPlaces(Long organizationId);

  @Query("SELECT p.name, CONVERT(ST_AsText(p.position), CHAR) AS position, pd.address, pd.city, pd.state, pd.zipcode FROM Place p, PlaceData pd WHERE p.organizationId = :organizationId AND p.id = pd.id AND p.id = :placeId")
  public Mono<GetOrganizationOrganizationIdPlacePlaceIdresponse> findPlaceById(Long organizationId, Long placeId);

  //TODO refactor this query
  @Modifying
  @Query("INSERT INTO Place (name, position, organizationId) VALUES (:name, ST_PolygonFROMText(:position), :organizationId); SET @lastId := (SELECT id FROM Place ORDER BY id DESC limit 1); INSERT INTO PlaceData (id, address, city, state, zipcode) VALUES (@LastId, :address, :city, :state, :zipcode)")
  public Mono<Place> create(String name, String position, Long organizationId, String address, String city, String state, String zipcode);

  //TODO refactor this query
  @Modifying
  @Query("UPDATE Place pla, PlaceData pd SET pla.name = :name, pd.address = :address, pd.city = :city, pd.state = :state, pd.zipcode = :zipcode, pla.position = ST_PolygonFROMText(:position) WHERE pd.id = pla.id AND pla.id = :placeId AND pla.organizationId = :organizationId")
  //@Query("UPDATE Place pla,PlaceData pd, Polygon pol SET pla.name = :name, pd.address = :address, pd.city = :city, pd.zipcode = :zipcode, pd.state = :state WHERE o.id = :id")
  //public Flux<Place> update(Long orgId,Long id, String name, String address, String city, String zipcode, String state);
  public Mono<Void> updatePlaceById(String name, String address, String city, String state, String zipcode, String position, Long placeId, Long organizationId);

  @Modifying
  @Query("DELETE Place, PlaceData FROM Place INNER JOIN PlaceData WHERE Place.id = PlaceData.id AND Place.organizationId = :organizationId AND Place.id = :placeId")
  public Mono<Void> deletePlaceById(Long organizationId, Long placeId);

}

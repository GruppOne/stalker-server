package tech.gruppone.stalker.server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.db.PlacePositionDao;
import tech.gruppone.stalker.server.repositories.PlacePositionRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PlacePositionService {

  ObjectMapper jacksonObjectMapper;

  PlacePositionRepository placePositionRepository;

  public Mono<List<GeographicalPoint>> findGeographicalPointsByPlaceId(final Long id) {
    return placePositionRepository
        .findById(id)
        .map(PlacePositionDao::getRawPositionJson)
        .map(this::convertRawPositionJson);
  }

  public Mono<Integer> save(final Long placeId, final List<GeographicalPoint> geographicalPoints) {
    log.info("saving position for place {}", placeId);

    final String rawPositionJson = convertGeographicalPoints(geographicalPoints);

    final var createdPlacePositionNumber = placePositionRepository.save(placeId, rawPositionJson);

    createdPlacePositionNumber.subscribe(
        howMany -> log.info("if 1 then placeposition was created: {}", howMany));

    return createdPlacePositionNumber;
  }

  public Mono<Integer> update(
      final Long placeId, final List<GeographicalPoint> geographicalPoints) {

    return placePositionRepository.update(placeId, convertGeographicalPoints(geographicalPoints));
  }

  List<GeographicalPoint> convertRawPositionJson(final String rawPositionJson) {
    JsonNode coordinates;
    try {
      coordinates = jacksonObjectMapper.readTree(rawPositionJson).at("/coordinates/0");
    } catch (final JsonProcessingException e) {
      log.error(e.getMessage());

      return List.of();
    }

    final Iterable<JsonNode> iterable = coordinates::elements;

    final var geographicalPoints =
        StreamSupport.stream(iterable.spliterator(), false)
            .sequential()
            .map(
                node -> {
                  final double latitude = Double.parseDouble(node.get(0).asText());
                  final double longitude = Double.parseDouble(node.get(1).asText());

                  return new GeographicalPoint(latitude, longitude);
                })
            .collect(Collectors.toList());

    // drop last point if list is not empty.
    // I don't know if a single-point polygon is allowed by the GeoJSON spec.
    if (geographicalPoints.size() > 1) {
      geographicalPoints.remove(geographicalPoints.size() - 1);
    }

    return geographicalPoints;
  }

  String convertGeographicalPoints(final List<GeographicalPoint> geographicalPoints) {
    final ObjectNode jsonNode = jacksonObjectMapper.createObjectNode().put("type", "Polygon");
    final ArrayNode externalCoordinates = jsonNode.putArray("coordinates");
    final ArrayNode innerCoordinates = jacksonObjectMapper.createArrayNode();

    externalCoordinates.add(innerCoordinates);

    if (geographicalPoints.isEmpty()) {
      return jsonNode.toString();
    }

    final var first = geographicalPoints.get(0);
    final var last = geographicalPoints.get(geographicalPoints.size() - 1);

    if (first.getLatitude() == last.getLatitude() && first.getLongitude() == last.getLongitude()) {
      log.info("the last polygon point is already equal to the first point.");
    } else {
      geographicalPoints.add(new GeographicalPoint(first.getLatitude(), first.getLongitude()));
    }

    geographicalPoints.stream()
        .forEachOrdered(
            geoPoint -> {
              final ArrayNode pointTuple =
                  jacksonObjectMapper
                      .createArrayNode()
                      .add(geoPoint.getLatitude())
                      .add(geoPoint.getLongitude());

              innerCoordinates.add(pointTuple);
            });

    // returns an empty polygon if parsing failed
    return jsonNode.toString();
  }
}

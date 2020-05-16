package tech.gruppone.stalker.server.services;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Service
public class PlaceSerializationService {

  ObjectMapper jacksonObjectMapper;

  // FIXME should I drop the last point?
  public List<GeographicalPoint> convertRawPositionJson(final String rawPositionJson) {
    JsonNode coordinates;
    try {
      coordinates = jacksonObjectMapper.readTree(rawPositionJson).at("/coordinates/0");

      final Iterable<JsonNode> iterable = coordinates::elements;

      return StreamSupport.stream(iterable.spliterator(), false)
          .map(
              node -> {
                final double latitude = Double.parseDouble(node.get(0).asText());
                final double longitude = Double.parseDouble(node.get(1).asText());

                return new GeographicalPoint(latitude, longitude);
              })
          .collect(toList());

    } catch (final JsonProcessingException e) {
      log.error(e.getMessage());

      return Collections.emptyList();
    }
  }

  public String convertGeographicalPoints(final List<GeographicalPoint> geographicalPoints) {

    JsonNode jsonNode;
    try {
      final String baseJson = "{\"type\": \"Polygon\", \"coordinates\": [[]]}";
      jsonNode = jacksonObjectMapper.readTree(baseJson);
    } catch (final JsonProcessingException e) {
      jsonNode = jacksonObjectMapper.createObjectNode();
    }

    if (geographicalPoints.isEmpty()) {
      return jsonNode.toString();
    }

    final ArrayNode innerCoordinates = (ArrayNode) jsonNode.get("coordinates").get(0);

    final var first = geographicalPoints.get(0);
    final var last = geographicalPoints.get(geographicalPoints.size() - 1);

    if (!first.equals(last)) {
      geographicalPoints.add(new GeographicalPoint(first.getLatitude(), first.getLongitude()));
    } else {
      log.info("the last polygon point was already present.");
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

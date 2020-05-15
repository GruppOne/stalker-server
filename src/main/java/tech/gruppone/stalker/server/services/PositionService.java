package tech.gruppone.stalker.server.services;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;

@Value
@NonFinal
@Service
@Log4j2
// TODO should probably rename this service
public class PositionService {

  ObjectMapper jacksonObjectMapper;

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

    final String baseJson = "{\"type\": \"Polygon\", \"coordinates\": [[]]}";
    JsonNode jsonNode;
    try {
      jsonNode = jacksonObjectMapper.readTree(baseJson);
    } catch (final JsonProcessingException e) {
      jsonNode = jacksonObjectMapper.createObjectNode();
    }

    final ArrayNode innerCoordinates = (ArrayNode) jsonNode.get("coordinates").get(0);

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

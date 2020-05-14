package tech.gruppone.stalker.server.services;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PositionService {

  ObjectMapper jacksonObjectMapper;

  public List<GeographicalPoint> convertRawPositionJson(String rawPositionJson) {
    JsonNode coordinates;
    try {
      coordinates = jacksonObjectMapper.readTree(rawPositionJson).at("/coordinates/0");

      Iterable<JsonNode> iterable = coordinates::elements;

      return StreamSupport.stream(iterable.spliterator(), false)
          .map(
              node -> {
                double latitude = Double.parseDouble(node.get(0).asText());
                double longitude = Double.parseDouble(node.get(1).asText());

                return new GeographicalPoint(latitude, longitude);
              })
          .collect(toList());

    } catch (JsonProcessingException e) {
      log.error(e.getMessage());

      return Collections.emptyList();
    }
  }
}

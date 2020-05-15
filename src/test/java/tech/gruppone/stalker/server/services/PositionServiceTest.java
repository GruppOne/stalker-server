package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;

@SpringBootTest
class PositionServiceTest {

  @Autowired private PositionService positionService;

  @Test
  void testConvertRawPositionJson() {
    List<GeographicalPoint> expectedPoints = Arrays.asList(new GeographicalPoint(50.0, 50.0));
    String rawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";

    var sut = positionService.convertRawPositionJson(rawPositionJson);

    assertThat(sut).isEqualTo(expectedPoints);
  }

  @Test
  void testConvertGeographicalPoints() {
    String expectedRawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";
    List<GeographicalPoint> geographicalPoints = Arrays.asList(new GeographicalPoint(50.0, 50.0));

    var sut = positionService.convertGeographicalPoints(geographicalPoints);

    assertThat(sut).isEqualTo(expectedRawPositionJson);
  }
}

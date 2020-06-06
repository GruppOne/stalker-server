package tech.gruppone.stalker.server.services;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.repositories.PlacePositionRepository;

@SpringBootTest
class PlacePositionServiceTest {

  @MockBean PlacePositionRepository placePositionRepository;

  // need to autowire the objectmapper
  @Autowired PlacePositionService placePositionService;

  @Test
  void testConvertRawPositionJson() {
    List<GeographicalPoint> expectedPoints = Arrays.asList(new GeographicalPoint(50.0, 50.0));
    String rawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";

    var sut = placePositionService.convertRawPositionJson(rawPositionJson);

    assertThat(sut).isEqualTo(expectedPoints);
  }

  @Test
  void testConvertGeographicalPoints() {
    String expectedRawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";
    List<GeographicalPoint> geographicalPoints = Arrays.asList(new GeographicalPoint(50.0, 50.0));

    var sut = placePositionService.convertGeographicalPoints(geographicalPoints);

    assertThat(sut).isEqualTo(expectedRawPositionJson);
  }

  static Stream<Arguments> testConvertGeographicalPointsParameters() {
    return Stream.of(
        arguments(List.of(), "{\"type\":\"Polygon\",\"coordinates\":[[]]}"),
        arguments(
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(1.0, 1.0),
                    new GeographicalPoint(0.0, 0.0))
                .collect(toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,0.0]]]}"),
        arguments(
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(1.0, 1.0))
                .collect(toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,0.0]]]}"));
  }

  @ParameterizedTest
  @MethodSource("testConvertGeographicalPointsParameters")
  void testConvertManyGeographicalPoints(
      List<GeographicalPoint> geographicalPoints, String expectedRawPositionJson) {

    var sut = placePositionService.convertGeographicalPoints(geographicalPoints);

    assertThat(sut).isEqualTo(expectedRawPositionJson);
  }
}

package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.gruppone.stalker.server.model.api.PlaceDataDto.GeographicalPoint;
import tech.gruppone.stalker.server.model.db.PlacePositionDao;
import tech.gruppone.stalker.server.repositories.PlacePositionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class PlacePositionServiceTest {

  @MockBean PlacePositionRepository placePositionRepository;

  // need to autowire the jackson objectmapper
  @Autowired PlacePositionService placePositionService;

  // raw position json string saved and selected from db. always has last point == first point
  static final String rawPositionJson =
      "{\"type\":\"Polygon\",\"coordinates\":[[[11.88745379447937,45.41154543369606],[11.887276768684385,45.4112065041318],[11.887807846069336,45.41108976125539],[11.888017058372496,45.411421159758675],[11.88745379447937,45.41154543369606]]]}";

  // geographical points managed by the API
  static final List<GeographicalPoint> pointsWithoutLastPoint =
      List.of(
          new GeographicalPoint(11.88745379447937, 45.41154543369606),
          new GeographicalPoint(11.887276768684385, 45.4112065041318),
          new GeographicalPoint(11.887807846069336, 45.41108976125539),
          new GeographicalPoint(11.888017058372496, 45.411421159758675));
  static final List<GeographicalPoint> pointsWithLastPoint =
      List.of(
          new GeographicalPoint(11.88745379447937, 45.41154543369606),
          new GeographicalPoint(11.887276768684385, 45.4112065041318),
          new GeographicalPoint(11.887807846069336, 45.41108976125539),
          new GeographicalPoint(11.888017058372496, 45.411421159758675),
          new GeographicalPoint(11.88745379447937, 45.41154543369606));

  @Test
  void testConvertRawPositionJsonMalformed() {
    String rawPositionJson = "not json";

    final var sut = placePositionService.convertRawPositionJson(rawPositionJson);

    assertThat(sut).isEqualTo(List.of());
  }

  @Test
  void testConvertRawPositionJsonSimple() {
    List<GeographicalPoint> expectedPoints = List.of(new GeographicalPoint(50.0, 50.0));
    String rawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";

    final var sut = placePositionService.convertRawPositionJson(rawPositionJson);

    assertThat(sut).isEqualTo(expectedPoints);
  }

  @Test
  void testConvertRawPositionJsonComplex() {
    final var sut =
        placePositionService.convertRawPositionJson(PlacePositionServiceTest.rawPositionJson);

    assertThat(sut).isEqualTo(pointsWithoutLastPoint);
  }

  @Test
  void testConvertGeographicalPoints() {
    final String expectedRawPositionJson = "{\"type\":\"Polygon\",\"coordinates\":[[[50.0,50.0]]]}";
    final List<GeographicalPoint> geographicalPoints =
        Arrays.asList(new GeographicalPoint(50.0, 50.0));

    final var sut = placePositionService.convertGeographicalPoints(geographicalPoints);

    assertThat(sut).isEqualTo(expectedRawPositionJson);
  }

  static Stream<Arguments> testConvertGeographicalPointsParameters() {
    return Stream.of(
        arguments(List.of(), "{\"type\":\"Polygon\",\"coordinates\":[[]]}"),
        arguments(
            // fastest way of creating a modifiable list
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(1.0, 1.0),
                    new GeographicalPoint(0.0, 0.0))
                .collect(Collectors.toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,0.0]]]}"),
        arguments(
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(1.0, 1.0))
                .collect(Collectors.toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[1.0,1.0],[0.0,0.0]]]}"),
        arguments(
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(0.0, 2.0))
                .collect(Collectors.toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[0.0,2.0],[0.0,0.0]]]}"),
        arguments(
            Stream.of(
                    new GeographicalPoint(0.0, 0.0),
                    new GeographicalPoint(0.0, 1.0),
                    new GeographicalPoint(1.0, 0.0))
                .collect(Collectors.toList()),
            "{\"type\":\"Polygon\",\"coordinates\":[[[0.0,0.0],[0.0,1.0],[1.0,0.0],[0.0,0.0]]]}"),
        // ensure static variables are not modified
        arguments(pointsWithoutLastPoint.stream().collect(Collectors.toList()), rawPositionJson),
        arguments(pointsWithLastPoint.stream().collect(Collectors.toList()), rawPositionJson));
  }

  @ParameterizedTest
  @MethodSource("testConvertGeographicalPointsParameters")
  void testConvertManyGeographicalPoints(
      final List<GeographicalPoint> geographicalPoints, final String expectedRawPositionJson) {

    final var sut = placePositionService.convertGeographicalPoints(geographicalPoints);

    assertThat(sut).isEqualTo(expectedRawPositionJson);
  }

  @Test
  void testFindGeographicalPointsByPlaceId() {

    final var placeId = 1L;

    final var placePosition =
        new PlacePositionDao(placeId, PlacePositionServiceTest.rawPositionJson);

    when(placePositionRepository.findById(placeId)).thenReturn(Mono.just(placePosition));

    final var sut = placePositionService.findGeographicalPointsByPlaceId(placeId);

    StepVerifier.create(sut)
        .expectNext(PlacePositionServiceTest.pointsWithoutLastPoint)
        .verifyComplete();

    verify(placePositionRepository).findById(placeId);
  }

  @Test
  void testSave() {

    final var placeId = 1L;

    when(placePositionRepository.save(placeId, rawPositionJson)).thenReturn(Mono.just(1));

    final var sut =
        placePositionService.save(placeId, PlacePositionServiceTest.pointsWithLastPoint);

    StepVerifier.create(sut).expectNext(1).verifyComplete();

    verify(placePositionRepository).save(placeId, rawPositionJson);
  }

  @Test
  void testUpdate() {

    final var placeId = 1L;

    when(placePositionRepository.update(placeId, rawPositionJson)).thenReturn(Mono.just(1));

    final var sut =
        placePositionService.update(placeId, PlacePositionServiceTest.pointsWithLastPoint);

    StepVerifier.create(sut).expectNext(1).verifyComplete();

    verify(placePositionRepository).update(placeId, rawPositionJson);
  }
}

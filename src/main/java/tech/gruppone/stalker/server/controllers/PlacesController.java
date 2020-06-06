package tech.gruppone.stalker.server.controllers;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.PlaceDataDto;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.services.PlaceService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/places")
public class PlacesController {

  PlaceService placeService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationByIdPlacesResponse> getOrganizationByIdPlaces(
      @PathVariable("organizationId") final long organizationId) {

    return placeService
        .findAllByOrganizationId(organizationId)
        .collectList()
        .map(GetOrganizationByIdPlacesResponse::new);
  }

  @Value
  static class GetOrganizationByIdPlacesResponse {
    List<PlaceDto> places;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<PostOrganizationByIdPlacesResponse> postOrganizationByIdPlaces(
      @PathVariable("organizationId") final long organizationId,
      @RequestBody PlaceDataDto placeDataDto) {

    return placeService
        .save(organizationId, placeDataDto)
        .map(PostOrganizationByIdPlacesResponse::new);
  }

  @Value
  static class PostOrganizationByIdPlacesResponse {
    long id;
  }

  @GetMapping("/report")
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  public Mono<Throwable> getOrganizationByIdPlacesReport(
      @PathVariable("organizationId") final long organizationId) {

    return Mono.error(NotImplementedException::new);
  }
}

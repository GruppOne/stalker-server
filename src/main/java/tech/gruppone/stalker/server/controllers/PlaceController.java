package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.PlaceDto;
import tech.gruppone.stalker.server.repositories.PlaceRepository;
import tech.gruppone.stalker.server.services.PlaceService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/place/{placeId}")
public class PlaceController {

  PlaceService placeService;
  PlaceRepository placeRepository;

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("placeId") final long placeId) {

    return placeRepository.deleteById(placeId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<PlaceDto> getOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("placeId") final long placeId) {

    return placeService.findById(placeId, organizationId);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("placeId") final long placeId,
      @RequestBody PlaceDto placeDto) {

    return placeService.updateById(placeId, organizationId, placeDto.getData());
  }
}

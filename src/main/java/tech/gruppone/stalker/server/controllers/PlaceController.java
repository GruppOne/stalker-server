package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.Place;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdPlacePlaceIdresponse;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@RequestMapping("/organization/{organizationId}/place/{placeId}")
@RestController
@Value
public class PlaceController {

  private PlaceRepository placeRepository;

  //TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationOrganizationIdPlacePlaceIdresponse> getPlacesByOrganizationId(@PathVariable("organizationId") final Long organizationId, @PathVariable("placeId") final Long placeId) {

    return Mono.error(new NotImplementedException());
    // return placeRepository.findPlaceById(organizationId, placeId);
  }

  //TODO
  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putPlaceById(@PathVariable("organizationId") final Long organizationId, @PathVariable("placeId") final Long placeId, @RequestBody final Place body) throws IOException{

    return Mono.error(new NotImplementedException());
    // return placeRepository.updatePlaceById(body.getName(), body.getAddress(), body.getCity(), body.getState(), body.getZipcode(), body.getPosition(), placeId, organizationId);
  }

  //TODO who verify if the user who sent this request has the privileges? Web app or we server?
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deletePlaceById(@PathVariable("organizationId") final Long organizationId, @PathVariable("placeId") final Long placeId) {

    return placeRepository.deletePlaceById(organizationId, placeId);
  }

}


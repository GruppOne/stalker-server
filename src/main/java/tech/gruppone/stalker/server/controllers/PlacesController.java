package tech.gruppone.stalker.server.controllers;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.Place;
import tech.gruppone.stalker.server.model.api.PlaceData;
import tech.gruppone.stalker.server.model.api.responses.GetOrganizationOrganizationIdPlacesResponse;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@RequestMapping("/organization/{organizationId}/places")
@RestController
@Value
public class PlacesController {

  private PlaceRepository placeRepository;

  //TODO
  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Mono<GetOrganizationOrganizationIdPlacesResponse> getPlaces(@PathVariable final Long organizationId) {

    return Mono.error(new NotImplementedException());
    // return placeRepository.findAllPlaces(organizationId);
  }

  //TODO
  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Place> createPlace(@PathVariable final Long organizationId, @RequestBody final PlaceData body) throws IOException{

    return Mono.error(new NotImplementedException());
    // return placeRepository.create(body.getName(), body.getPosition(), organizationId, body.getAddress(), body.getCity(), body.getState(), body.getZipcode());
  }



}

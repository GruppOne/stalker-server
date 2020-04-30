package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Place;
import tech.gruppone.stalker.server.repository.PlaceRepository;

@RequestMapping("/organization/{organizationId}/place/{placeId}")
@RestController
@Value
public class PlaceController {

  PlaceRepository placeRepository;


  @GetMapping
  public Mono<Place> getPlacesByOrganizationId(@PathVariable("organizationId") final Long organizationId,@PathVariable("placeId") final Long placeId) {

    return placeRepository.find(organizationId, placeId);
  }


  @PutMapping
  public Mono<Place> putPlaceById(@PathVariable("organizationId") final Long organizationId, @PathVariable("userId") final Long placeId, @RequestBody String jsonString) throws IOException{

    Place place = new ObjectMapper().readValue(jsonString, Place.class);
    return placeRepository.update(place.getName(), place.getAddress(), place.getCity(), place.getState(), place.getZipcode(), place.getPosition(), placeId, organizationId);
  }


  @DeleteMapping
  public Mono<Place> deletePlaceById(@PathVariable("organizationId") final Long organizationId,@PathVariable("userId") final Long placeId) {

    return placeRepository.deletePlaceById(organizationId, placeId);
  }

}


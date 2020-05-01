package tech.gruppone.stalker.server.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.Place;
import tech.gruppone.stalker.server.repositories.PlaceRepository;

@RequestMapping("/organization/{orgId}/places")
@RestController
@Value
public class PlacesController {

  PlaceRepository placeRepository;


  @PostMapping
  public Mono<Place> createPlace(@PathVariable Long orgId, @RequestBody String jsonString) throws IOException{
    return Mono.empty();
    // Place place = new ObjectMapper().readValue(jsonString, Place.class);
    // System.out.println(place.getPosition());
    // return placeRepository.create(place.getName(),place.getPosition(),orgId,place.getAddress(),place.getCity(),place.getState(),place.getZipcode());
  }


  @GetMapping
  public Flux<Place> getPlaces(@PathVariable Long orgId) {

    return placeRepository.findAll(orgId);
  }

}

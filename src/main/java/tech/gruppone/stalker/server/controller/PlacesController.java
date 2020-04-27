package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.Place;
import tech.gruppone.stalker.server.repository.PlaceRepository;

@RequestMapping("/organization/{orgId}/places")
@RestController
@Value
public class PlacesController {

  PlaceRepository placeRepository;


  @PostMapping
  public Flux<Place> createPlace(@PathVariable Long orgId,@RequestBody String jsonString) throws IOException{

    Place p = new ObjectMapper().readValue(jsonString, Place.class);
    return placeRepository.create(p.getName(), orgId);
  }


  @GetMapping
  public Flux<Place> getPlaces(@PathVariable Long orgId) {

    return placeRepository.findAll(orgId);
  }

}

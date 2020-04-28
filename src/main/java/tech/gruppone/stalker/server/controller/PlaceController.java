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

@RequestMapping("/organization/{orgId}/place")
@RestController
@Value
public class PlaceController {

  PlaceRepository placeRepository;


  @GetMapping("/{id}")
  public Mono<Place> getPlaces(@PathVariable Long orgId,@PathVariable Long id) {

    return placeRepository.find(orgId,id);
  }


  @PutMapping("/{id}")
  public Mono<Place> updatePlace(@PathVariable Long orgId,@PathVariable Long id,@RequestBody String jsonString) throws IOException{

    Place p = new ObjectMapper().readValue(jsonString, Place.class);
    return placeRepository.update(p.getName(),p.getAddress(),p.getCity(),p.getState(),p.getZipcode(),p.getPosition(),id,orgId);
  }


  @DeleteMapping("/{id}")
  public Mono<Place> deletePlace(@PathVariable Long orgId,@PathVariable Long id) {
    return placeRepository.delete(orgId,id);
  }

}


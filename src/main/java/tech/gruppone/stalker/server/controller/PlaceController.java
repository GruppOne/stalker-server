package tech.gruppone.stalker.server.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.Place;
import tech.gruppone.stalker.server.repository.PlaceRepository;

@RequestMapping("/organization/{orgId}/place")
@RestController
@Value
public class PlaceController {

  PlaceRepository placeRepository;



  @PutMapping("/{id}")
  public Flux<Place> updatePlace(@PathVariable Long orgId,@PathVariable Long id,@RequestBody String jsonString) throws IOException{

    Place p = new ObjectMapper().readValue(jsonString, Place.class);
    return placeRepository.update(p.getName(),id,orgId);
  }


  @DeleteMapping("/{id}")
  public Flux<Place> deletePlace(@PathVariable Long orgId,@PathVariable Long id) {
    return placeRepository.delete(orgId,id);
  }

}


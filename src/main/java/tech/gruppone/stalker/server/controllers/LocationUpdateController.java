package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfoDto;
import tech.gruppone.stalker.server.services.LocationService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class LocationUpdateController {

  LocationService locationInfoService;

  @PostMapping("/location/update")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postLocationUpdate(@RequestBody final MultiLocationInfoDto multiLocationInfo) {

    return locationInfoService.saveMulti(multiLocationInfo).then();
  }
}

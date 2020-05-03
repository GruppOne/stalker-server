package tech.gruppone.stalker.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfo;
import tech.gruppone.stalker.server.repository.LocationInfoRepository;
import tech.gruppone.stalker.server.services.LocationInfoService;

@Log4j2
@RestController
@Value
@NonFinal
public class MultiLocationInfoController {

  @NonNull
  LocationInfoService locationInfoService;

  @NonNull
  LocationInfoRepository locationInfoRepository;

  @PostMapping("/location/update")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postLocationUpdate(@RequestBody final MultiLocationInfo multiLocationInfo) {
    log.info(multiLocationInfo);

    var allLocationInfo = locationInfoService.locationInfoSupplier(multiLocationInfo);
    var savedLocationInfo = locationInfoRepository.saveAllLocationInfo(allLocationInfo);

    return savedLocationInfo.then();
  }

}

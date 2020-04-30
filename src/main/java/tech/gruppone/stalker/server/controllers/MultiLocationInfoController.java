package tech.gruppone.stalker.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.MultiLocationInfo;

@Log4j2
@RestController
public class MultiLocationInfoController {

  @PostMapping("/location/update")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postLocationUpdate(@RequestBody MultiLocationInfo multiLocationInfo) {
    log.info(multiLocationInfo);

    return Mono.empty();
  }

}

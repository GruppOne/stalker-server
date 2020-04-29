package tech.gruppone.stalker.server.controller;

import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Flux;
// import lombok.extern.log4j.Log4j2;
import tech.gruppone.stalker.server.model.OrganizationData;
import tech.gruppone.stalker.server.repository.ConnectionRepository;

@Value
@RestController
public class ConnectionController {

  private ConnectionRepository connectionRepository;

  @GetMapping("/user/{id}/organizations/connections")
  public Flux<OrganizationData> getConnectedOrganizationsByUserId(@PathVariable final Long id){
    return connectionRepository.findConnectedOrganizationsByUserId(id);
  }
}

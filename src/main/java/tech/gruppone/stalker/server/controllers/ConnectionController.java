package tech.gruppone.stalker.server.controllers;

import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Flux;
// import lombok.extern.log4j.Log4j2;
import tech.gruppone.stalker.server.models.api.OrganizationData;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@Value
@RestController
@RequestMapping("/user/{id}/organizations/connections")
public class ConnectionController {

  private ConnectionRepository connectionRepository;

  //TODO verify if @RequestBody Connection connection works
  @GetMapping
  public Flux<OrganizationData> getConnectedOrganizationsByUserId(@PathVariable final Long id){
    return connectionRepository.findConnectedOrganizationsByUserId(id);
  }

}

package tech.gruppone.stalker.server.controller;

import org.springframework.web.bind.annotation.*;
import lombok.Value;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.ConnectedOrganization;
import tech.gruppone.stalker.server.repository.ConnectedRepository;

@Value
@RestController
@RequestMapping("/user")
public class ConnectedController {

  private ConnectedRepository connectedRepository;

  @GetMapping("/{id}/organizations/connections")
  public Flux<ConnectedOrganization> getListConnectedOrganizationsById(@PathVariable Long id){
    return connectedRepository.findOrganizationListById(id);
  }
}

package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;
import tech.gruppone.stalker.server.model.api.OrganizationDto;
import tech.gruppone.stalker.server.repositories.OrganizationRepository;
import tech.gruppone.stalker.server.services.OrganizationService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{id}")
public class OrganizationController {

  OrganizationService organizationService;
  OrganizationRepository organizationRepository;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<OrganizationDto> getOrganizationById(@PathVariable final long id) {
    return organizationService.findById(id);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationById(
      @PathVariable final long id, @RequestBody OrganizationDto organizationDto) {

    return organizationService.updateById(id, organizationDto.getData());
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationById(@PathVariable final long id) {

    return organizationRepository.deleteById(id);
  }

  @GetMapping("/users/inside")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getOrganizationByIdUsersInside(@PathVariable final long id) {

    return Mono.error(NotImplementedException::new);
  }
}

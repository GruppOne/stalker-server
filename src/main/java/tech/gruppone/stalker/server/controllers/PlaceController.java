package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.exceptions.NotImplementedException;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/organization/{organizationId}/place/{placeId}")
public class PlaceController {

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<Throwable> getOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> putOrganizationByIdPlaceById(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return Mono.error(NotImplementedException::new);
  }
}

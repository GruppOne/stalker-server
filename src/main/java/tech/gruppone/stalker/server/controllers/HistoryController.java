package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserHistoryDto;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto;
import tech.gruppone.stalker.server.services.HistoryService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class HistoryController {

  HistoryService historyService;

  @GetMapping("/organization/{organizationId}/user/{userId}/history")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserHistoryPerOrganizationDto> getOrganizationByIdUserByIdHistory(
      @PathVariable("organizationId") final long organizationId,
      @PathVariable("userId") final long userId) {

    return historyService.findHistoryByOrganizationIdAndUserId(organizationId, userId);
  }

  @GetMapping("/user/{userId}/history")
  @ResponseStatus(HttpStatus.OK)
  public Mono<UserHistoryDto> getUserByIdHistory(@PathVariable("userId") final long userId) {

    return historyService.findHistoryByUserId(userId);
  }
}

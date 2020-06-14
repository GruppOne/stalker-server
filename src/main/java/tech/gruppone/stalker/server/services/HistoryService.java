package tech.gruppone.stalker.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.api.UserHistoryDto;
import tech.gruppone.stalker.server.model.api.UserHistoryDto.OrganizationHistoryDto;
import tech.gruppone.stalker.server.model.api.UserHistoryPerOrganizationDto;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.repositories.LocationInfoRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class HistoryService {
  ConnectionRepository connectionRepository;

  LocationInfoRepository locationInfoRepository;

  public Mono<UserHistoryPerOrganizationDto> findHistoryByOrganizationIdAndUserId(
      final long organizationId, final long userId) {

    return locationInfoRepository
        .findHistoryByOrganizationIdAndUserId(organizationId, userId)
        .collectList()
        .map(history -> new UserHistoryPerOrganizationDto(userId, history));
  }

  public Mono<UserHistoryDto> findHistoryByUserId(final long userId) {

    return connectionRepository
        .findConnectedOrganizationIdsByUserId(userId)
        .flatMap(
            connectedOrganizationId ->
                Mono.just(connectedOrganizationId)
                    .zipWith(findHistoryByOrganizationIdAndUserId(connectedOrganizationId, userId)))
        .map(
            tuple -> {
              final var organizationId = tuple.getT1();
              final var historyPerOrganization = tuple.getT2();

              return new OrganizationHistoryDto(organizationId, historyPerOrganization);
            })
        .collectList()
        .map(UserHistoryDto::new);
  }
}

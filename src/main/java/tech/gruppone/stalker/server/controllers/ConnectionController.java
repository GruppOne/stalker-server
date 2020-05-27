package tech.gruppone.stalker.server.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.db.ConnectionDao;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/{userId}/organization/{organizationId}/connection")
public class ConnectionController {

  ConnectionRepository connectionRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> postUserByIdOrganizationByIdConnection(
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") final long organizationId,
      @RequestBody(required = false) final PostUserByIdOrganizationByIdConnectionBody requestBody) {

    if (requestBody != null) {
      log.info("connecting to private organization. Request body is: {}", requestBody);
      // TODO implement functionality. request should fail if given rdn + pw are not valid.
    }

    final ConnectionDao connectionDao =
        ConnectionDao.builder().userId(userId).organizationId(organizationId).build();

    // TODO handle error. return 400 if connection is already present!
    return connectionRepository.save(connectionDao).then();
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteUserByIdOrganizationByIdConnection(
      @PathVariable("userId") final long userId,
      @PathVariable("organizationId") final long organizationId) {
    return connectionRepository.deleteByUserIdAndOrganizationId(userId, organizationId).then();
  }

  @Value
  private static class PostUserByIdOrganizationByIdConnectionBody {

    @NonNull LdapAuthentication ldapAuthentication;

    @Value
    private static class LdapAuthentication {
      @NonNull String rdn;
      @NonNull String ldapPassword;
    }
  }
}

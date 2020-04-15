package tech.gruppone.stalker.server.repository;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Organization;

public class OrganizationRepository {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationRepository.class);

  final Map<Integer, Organization> organizationsMap = new HashMap<Integer, Organization>() {{
    put(
      1,
      Organization.builder()
        .id(1)
        .name("Dipartimento di Informatica dell'Università di Padova")
        .description("...")
        .isPrivate(false)
        .build()
    );
    put(2, Organization.builder().id(2).name("Imola Informatica").description("...").isPrivate(false).build());
  }};

  public Organization[] findAllOrganizations() {
    logger.info("getting all organizations from repository");

    return organizationsMap.values().toArray(Organization[]::new);
  }

  public Mono<Organization> findOrganizationById(int id) {
    logger.info("searching for an organization with id {}", id);

    if (!organizationsMap.containsKey(id)) {

      return Mono.error(() -> new Throwable("organization not found"));
    } else {

      Organization requestedOrganization = organizationsMap.get(id);
      return Mono.just(requestedOrganization);
    }
  }

}

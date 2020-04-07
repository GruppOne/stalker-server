package tech.gruppone.stalkerserver.organization;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class OrganizationRepository {

  private static final Logger logger = LoggerFactory.getLogger(OrganizationRepository.class);

  final Map<Integer, Organization> organizationsMap = new HashMap<Integer, Organization>() {{
    //noinspection GrazieInspection
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

    Organization[] organizations = organizationsMap.values().toArray(Organization[]::new);

    return organizations;
  }

  public Mono<Organization> findOrganizationById(int id) {
    //noinspection GrazieInspection
    logger.info("searching for an organization with id {}", id);

    if (!organizationsMap.containsKey(id)) {

      return Mono.error(() -> new Throwable("organization not found"));
    } else {

      Organization requestedOrganization = organizationsMap.get(id);
      return Mono.just(requestedOrganization);
    }
  }

}

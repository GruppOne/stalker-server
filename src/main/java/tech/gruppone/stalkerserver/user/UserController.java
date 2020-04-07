package tech.gruppone.stalkerserver.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.gruppone.stalkerserver.organization.Organization;
import tech.gruppone.stalkerserver.organization.OrganizationRepository;

@RequestMapping("/users")
@Controller
public class UserController {

  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  //  FIXME this should not be done like this
  private final OrganizationRepository organizationRepository = new OrganizationRepository();
  private final UserRepository userRepository = new UserRepository();

  // XXX is it correct to send the hashed password?
  @PostMapping("/login")
  public Mono<User> login(@RequestBody UnauthenticatedUser unauthenticatedUser) {

    logger.info("received login request for {}", unauthenticatedUser);

//    TODO check password is a match

    return userRepository.findByEmail(unauthenticatedUser.email);
  }

  @GetMapping("users/{id}/subscribed")
  public Flux<Organization> getSubscribedOrganizations(@PathVariable int id) {

    logger.info("requested subscribed organizations for user {}", id);

    return organizationRepository.findAllOrganizations();
  }

}

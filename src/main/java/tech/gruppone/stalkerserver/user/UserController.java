package tech.gruppone.stalkerserver.user;

//@RequestMapping("/users")
//@RestController
public class UserController {

//  private static Logger logger = LoggerFactory.getLogger(UserController.class);
//
//  //  FIXME this should not be done like this
//  private final OrganizationRepository organizationRepository = new OrganizationRepository();
//  private final UserRepository userRepository = new UserRepository();
//
//  // XXX is it correct to send the hashed password?
//  @PostMapping("/login")
//  public Mono<User> login(@RequestBody UnauthenticatedUser unauthenticatedUser) {
//
//    logger.info("received login request for {}", unauthenticatedUser);
//
////    TODO check password is a match
//
//    return userRepository.findByEmail(unauthenticatedUser.email);
//  }

//  @GetMapping("/users/{id}/subscribed")
//  public Flux<Integer> getSubscribedOrganizations(@PathVariable("id") int id) {
//
//    logger.info("requested subscribed organizations for user {}", id);
//
//    Organization[] organizations = organizationRepository.findAllOrganizations();
//    var ids = Arrays.stream(organizations).map(org -> org.getId());
//
//    return Flux.fromStream(ids);
//  }

}

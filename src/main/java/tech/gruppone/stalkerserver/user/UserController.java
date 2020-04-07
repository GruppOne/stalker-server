package tech.gruppone.stalkerserver.user;

//import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
//import reactor.core.publisher.Mono;

@RequestMapping("/users")
@Controller
public class UserController {

  UserRepository userRepo = new UserRepository();
  private static Logger logger = LoggerFactory.getLogger(UserController.class);


  /*
  @PostMapping("/login")
  public Type login(@RequestMapping Map<String, String> body){
    String email = body.get("email");
    String password = body.get("password");
  }
  */
  @PostMapping("/login")
  public Mono<ResponseEntity<User>> login (@RequestBody UnauthenticatedUser user){
    if(userRepo.findByEmail(user.getEmail())){
        logger.info("user is logged");
        return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }
    else{
       logger.info("user is not logged");
       return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

  }
  /*
  @GetMapping("users/{id}/connected")
  public Type nameMethod(@PathVariable int id){}
  */
}

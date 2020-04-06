package tech.gruppone.stalkerserver.user;

//import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import reactor.core.publisher.Mono;

@RequestMapping("/users")
@Controller
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  /*
  @PostMapping("/login")
  public Type login(@RequestMapping Map<String, String> body){
    String email = body.get("email");
    String password = body.get("password");
  }
  */

  /*
  @GetMapping("users/{id}/connected")
  public Type nameMethod(@PathVariable int id){}
  */
}

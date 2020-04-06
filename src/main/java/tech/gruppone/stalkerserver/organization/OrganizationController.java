package tech.gruppone.stalkerserver.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.gruppone.stalkerserver.user.UserController;

@RequestMapping("/organizations")
@Controller
public class OrganizationController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  /*
  @GetMapping("/")
  public Type getOrganizations(){}
  */

  /*
  @PutMapping("/{id}")
  @RequestBody
  public Type nameMethod(@PathVariable int id){}
   */
}

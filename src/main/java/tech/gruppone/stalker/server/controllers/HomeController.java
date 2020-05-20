package tech.gruppone.stalker.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/ldap")
public class HomeController {

  @GetMapping
  public String index() {
    return "The LDAP authentication has been successful! Well done!";
  }

}

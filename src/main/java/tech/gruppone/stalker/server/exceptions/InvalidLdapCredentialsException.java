package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidLdapCredentialsException extends RuntimeException {
  public InvalidLdapCredentialsException() {
    super("The given LDAP credentials are not valid.");
  }
}

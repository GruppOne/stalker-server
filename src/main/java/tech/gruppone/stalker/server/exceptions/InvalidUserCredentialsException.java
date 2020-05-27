package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidUserCredentialsException extends RuntimeException {
  public InvalidUserCredentialsException() {
    super("This email/password combination is not valid.");
  }
}

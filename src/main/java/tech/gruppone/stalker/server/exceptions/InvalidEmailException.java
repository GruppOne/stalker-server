package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException() {

    super("The given email address is not valid for this request.");
  }
}

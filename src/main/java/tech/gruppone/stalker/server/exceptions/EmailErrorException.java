package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class EmailErrorException extends RuntimeException {

  public EmailErrorException() {

    super("It is not possible to execute this operation: email not registered in Stalker.");
  }
}

package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class EmailErrorException extends RuntimeException {

  public EmailErrorException(String message) {

    super(message);
  }
}

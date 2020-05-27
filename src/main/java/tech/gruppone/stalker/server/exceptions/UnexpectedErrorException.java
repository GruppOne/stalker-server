package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedErrorException extends RuntimeException {

  public UnexpectedErrorException() {
    super("The server encountered an unexpected error.");
  }
}

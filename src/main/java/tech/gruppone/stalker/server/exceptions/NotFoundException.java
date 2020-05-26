package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotFoundException extends RuntimeException {
  public NotFoundException() {
    super("The server could not find the requested resource");
  }
}

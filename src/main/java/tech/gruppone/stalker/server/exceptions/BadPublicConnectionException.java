package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadPublicConnectionException extends RuntimeException {

  public BadPublicConnectionException() {
    super(
        "The server could not process the request due to a client mistake: it is not possible to connect to a private organization without a request body.");
  }
}

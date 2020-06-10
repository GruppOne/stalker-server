package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadPrivateConnectionException extends RuntimeException {

  public BadPrivateConnectionException() {
    super(
        "The server could not process the request due to a client mistake: it is not possible to connect to a public organization with a request body.");
  }
}

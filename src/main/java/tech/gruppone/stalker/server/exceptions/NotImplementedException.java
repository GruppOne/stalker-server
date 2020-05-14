package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO decide how to handle this
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public final class NotImplementedException extends UnsupportedOperationException {

  public NotImplementedException() {
    super("NOT IMPLEMENTED YET");
  }

}

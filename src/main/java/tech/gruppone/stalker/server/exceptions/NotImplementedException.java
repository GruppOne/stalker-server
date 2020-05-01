package tech.gruppone.stalker.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
// TODO decide how to handle this
@SuppressWarnings("serial")
public final class NotImplementedException extends UnsupportedOperationException {

  public NotImplementedException() {
    super("NOT IMPLEMENTED YET");
  }

}

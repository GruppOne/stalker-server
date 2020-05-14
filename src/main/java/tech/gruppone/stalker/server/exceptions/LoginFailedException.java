package tech.gruppone.stalker.server.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
@AllArgsConstructor
public class LoginFailedException extends RuntimeException {

  private static final long serialVersionUID = 5;

  private String message;

}

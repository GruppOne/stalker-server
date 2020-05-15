package tech.gruppone.stalker.server.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UnauthorizedExceptionTest {

  @Test
  void getMessage() {

    UnauthorizedException exception = new UnauthorizedException();

    String expectedMessage = "The server could not authenticate the user.";
    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
  }
}

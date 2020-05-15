package tech.gruppone.stalker.server.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ForbiddenExceptionTest {

  @Test
  void getMessage() {
    ForbiddenException exception = new ForbiddenException();

    String expectedMessage = "The server could not authorize the user.";
    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
  }
}

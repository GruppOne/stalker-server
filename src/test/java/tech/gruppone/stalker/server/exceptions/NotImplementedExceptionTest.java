package tech.gruppone.stalker.server.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NotImplementedExceptionTest {

  @Test
  void getMessage() {

    NotImplementedException exception = new NotImplementedException();

    String expectedMessage = "NOT IMPLEMENTED YET";
    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
  }
}

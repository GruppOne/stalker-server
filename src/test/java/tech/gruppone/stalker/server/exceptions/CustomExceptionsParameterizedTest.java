package tech.gruppone.stalker.server.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomExceptionsParameterizedTest {
  static Stream<Arguments> testCustomExceptionMessagesParameters() {
    return Stream.of(
        arguments(
            BadRequestException.class,
            "The server could not process the request due to a client error."),
        arguments(ForbiddenException.class, "The server could not authorize the user."),
        arguments(
            InvalidLdapCredentialsException.class,
            "The LDAP credentials for this organization were invalid."),
        arguments(
            InvalidUserCredentialsException.class, "This email/password combination is not valid."),
        arguments(NotFoundException.class, "The server could not find the requested resource."),
        arguments(NotImplementedException.class, "NOT IMPLEMENTED YET"),
        arguments(UnauthorizedException.class, "The server could not authenticate the user."),
        arguments(UnexpectedErrorException.class, "The server encountered an unexpected error."));
  }

  @ParameterizedTest
  @MethodSource("testCustomExceptionMessagesParameters")
  void testCustomExceptionMessages(Class<RuntimeException> exceptionClass, String expectedMessage)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
          InvocationTargetException, NoSuchMethodException, SecurityException {

    var exception = exceptionClass.getConstructor().newInstance();

    assertThat(exception.getMessage()).isEqualTo(expectedMessage);
  }
}

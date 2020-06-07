package tech.gruppone.stalker.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.repositories.UserRepository;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordServiceTest {
  @MockBean UserRepository userRepository;

  @Autowired PasswordService passwordService;

  // TODO add missing unit test
  // @Test
  // void testResetPassword(){}
}

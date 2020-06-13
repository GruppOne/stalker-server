package tech.gruppone.stalker.server.services;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import tech.gruppone.stalker.server.ApplicationTestConfiguration;
import tech.gruppone.stalker.server.configuration.JwtConfiguration;

@Import(ApplicationTestConfiguration.class)
@SpringBootTest(
    webEnvironment = WebEnvironment.NONE,
    classes = {JwtService.class, JwtConfiguration.class})
class JwtServiceTest {

  @Autowired JwtService jwtService;
  @Autowired JwtConfiguration jwtConfiguration;

  @Test
  void testCreateToken() {
    final Long id = 1L;

    final var sut = jwtService.createToken(id);

    final Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(jwtConfiguration.getEncodedKey())
            .build()
            .parseClaimsJws(sut)
            .getBody();

    assertThat(claims.getSubject()).isEqualTo(String.valueOf(id));
    assertThat(claims.getIssuedAt())
        .isEqualToIgnoringMillis(
            Timestamp.valueOf(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME));
  }

  @Test
  void testCreateAnonymousToken() {
    final var sut = jwtService.createAnonymousToken();

    final Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(jwtConfiguration.getEncodedKey())
            .build()
            .parseClaimsJws(sut)
            .getBody();

    assertThat(claims.get("anonymous", Boolean.class)).isTrue();
    assertThat(claims.getIssuedAt())
        .isEqualToIgnoringMillis(
            Timestamp.valueOf(ApplicationTestConfiguration.FIXED_LOCAL_DATE_TIME));
  }
}

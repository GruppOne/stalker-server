package tech.gruppone.stalker.server.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("slow")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoryControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testGetOrganizationByIdUserByIdHistory() {

    assertTrue(false);
  }

  @Test
  void testGetUserByIdHistory() {

    assertTrue(false);
  }
}

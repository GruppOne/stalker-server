package tech.gruppone.stalker.server.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("slow")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlacesControllerTest {
  @Autowired WebTestClient webTestClient;

  @Test
  void testGetOrganizationByIdPlaces() {

    assertTrue(false);
  }

  @Test
  void testPostOrganizationByIdPlaces() {

    assertTrue(false);
  }

  @Test
  void testGetOrganizationByIdPlacesReport() {

    assertTrue(false);
  }
}

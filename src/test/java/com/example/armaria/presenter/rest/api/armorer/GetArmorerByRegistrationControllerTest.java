package com.example.armaria.presenter.rest.api.armorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.core.usecases.armorer.ArmorerCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetArmorerByRegistrationControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers/" + registration;

    ResponseEntity<Armorer> response = restTemplate.getForEntity(baseUrl, null, Armorer.class);
    assertNull(response.getBody());
  }

  @Test
  void test_createArmorer_before() {
    getArmorer();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers/" + registration;

    ResponseEntity<Armorer> response = restTemplate.getForEntity(baseUrl, Armorer.class);

    Armorer armorer = response.getBody();

    assertNotNull(armorer);
    assertEquals(registration, armorer.getRegistrationNumber());
  }

  private void getArmorer() {
    String createArmorerUrl = "http://localhost:" + port + "/api/v1/armorers";

    ArmorerCreateDTO armorerCreateDTO = new ArmorerCreateDTO(
        "any-registration",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(createArmorerUrl, armorerCreateDTO, String.class);
  }
}

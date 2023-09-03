package com.example.armaria.controllers.guarda_municipal;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.armaria.entities.GuardaMunicipal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuscarGuardaMunicipalPorMatriculaControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/guarda-municipal/" + matricula;

    ResponseEntity<GuardaMunicipal> response = restTemplate.getForEntity(baseUrl, null, GuardaMunicipal.class);
    assertNull(response.getBody());
  }
}

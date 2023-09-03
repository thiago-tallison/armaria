package com.example.armaria.controllers.armeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuscarArmeiroPorMatriculaControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;

    ResponseEntity<Armeiro> response = restTemplate.getForEntity(baseUrl, null, Armeiro.class);
    assertNull(response.getBody());
  }

  @Test
  void testHandle_quando_criar_armeiro_antes() {
    criarArmeiro();

    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;

    ResponseEntity<Armeiro> response = restTemplate.getForEntity(baseUrl, Armeiro.class);
    assertNotNull(response.getBody());
    assertEquals(matricula, response.getBody().getMatricula());
  }

  private void criarArmeiro() {
    String criarArmeiroUrl = "http://localhost:" + port + "/api/armeiro";

    CriarArmeiroDTO armeiroDTO = new CriarArmeiroDTO(
        "qualquer-matricula",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(criarArmeiroUrl, armeiroDTO, String.class);
  }
}

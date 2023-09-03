package com.example.armaria.controllers.armeiro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletarArmeiroControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    criarArmeiro();

    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;
    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void testHandle_deve_lancar_erro_para_armeiro_nao_encontrado() {
    String matricula = "qualquer-matricula-2";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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

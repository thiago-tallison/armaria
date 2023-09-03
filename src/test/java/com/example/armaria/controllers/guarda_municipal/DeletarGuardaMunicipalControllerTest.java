package com.example.armaria.controllers.guarda_municipal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletarGuardaMunicipalControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    criarGuardaMunicipal();

    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/guarda-municipal/" + matricula;
    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void testHandle_deve_lancar_erro_para_guarda_municipal_nao_encontrado() {
    String matricula = "matricula-nao-existente";
    String baseUrl = "http://localhost:" + port + "/api/guarda-municipal/" + matricula;

    ResponseEntity<Object> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Object.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  public void criarGuardaMunicipal() {
    String matricula = "qualquer-matricula";

    String baseUrl = "http://localhost:" + port + "/api/guarda-municipal/cadastrar";

    CriarGuardaMunicipalDTO guardaMunicipalDTO = new CriarGuardaMunicipalDTO(
        matricula,
        "João da Silva",
        "joao@example.com",
        "1234567890");

    restTemplate.postForEntity(baseUrl, guardaMunicipalDTO, String.class);
  }
}

package com.example.armaria.presenter.rest.api.armorer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.armaria.core.usecases.armorer.ArmorerCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateArmorerControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers";

    ArmorerCreateDTO armorerCreateDTO = new ArmorerCreateDTO(
        "123456",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, armorerCreateDTO, String.class);
    assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

  }

  @Test
  void testHandle_deve_retornar_400_se_corpo_requisicao_invalido() {
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers";

    ArmorerCreateDTO invalidArmorerCreateDTO = new ArmorerCreateDTO(
        "123456",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        null);

    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, invalidArmorerCreateDTO, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}

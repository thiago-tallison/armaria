package com.example.armaria.controllers.armeiro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriarArmeiroControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String baseUrl = "http://localhost:" + port + "/api/armeiro";

    CriarArmeiroDTO armeiroDTO = new CriarArmeiroDTO(
        "123456", // Matrícula
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, armeiroDTO, String.class);
    assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

  }
}

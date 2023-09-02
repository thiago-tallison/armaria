package com.example.armaria.controllers.guarda_municipal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CriarGuardaMunicipalControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testCriarGuardaMunicipal() {
    String baseUrl = "http://localhost:" + port + "/api/guarda-municipal/cadastrar";

    CriarGuardaMunicipalDTO guardaMunicipalDTO = new CriarGuardaMunicipalDTO(
        "123456", // Matrícula
        "João da Silva",
        "joao@example.com",
        "1234567890");

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, guardaMunicipalDTO, String.class);

    assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
  }
}

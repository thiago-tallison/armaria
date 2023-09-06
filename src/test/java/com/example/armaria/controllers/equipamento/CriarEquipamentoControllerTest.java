package com.example.armaria.controllers.equipamento;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CriarEquipamentoControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String baseUrl = "http://localhost:" + port + "/api/equipamento";

    CriarEquipamentoComItemDTO equipamentoComItemDto = new CriarEquipamentoComItemDTO("Nome do equipamento",
        "Numero de série", true, 10);

    ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, equipamentoComItemDto, Void.class);

    assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
  }
}

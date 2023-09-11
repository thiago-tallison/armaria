package com.example.armaria.controllers.armory_keeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateArmoryKeeperControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers";

    ArmoryKeeperCreateDTO armoryKeeperCreateDTO = new ArmoryKeeperCreateDTO(
        "123456",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, armoryKeeperCreateDTO, String.class);
    assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

  }

  @Test
  void testHandle_deve_retornar_400_se_corpo_requisicao_invalido() {
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers";

    ArmoryKeeperCreateDTO invalidArmoryKeeperCreateDTO = new ArmoryKeeperCreateDTO(
        "123456",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        null);

    ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, invalidArmoryKeeperCreateDTO, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}

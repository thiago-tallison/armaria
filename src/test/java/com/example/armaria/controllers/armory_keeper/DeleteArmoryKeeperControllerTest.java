package com.example.armaria.controllers.armory_keeper;

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

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteArmoryKeeperControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    getArmoryKeeper();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;
    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Void.class);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void when_ArmoryKeeperNotFound_ThrowsException() {
    String registration = "inexistent-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;

    ResponseEntity<Object> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Object.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  private void getArmoryKeeper() {
    String url = "http://localhost:" + port + "/api/v1/armory_keepers";

    ArmoryKeeperCreateDTO armoryKeeperCreateDTO = new ArmoryKeeperCreateDTO(
        "any-registration",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(url, armoryKeeperCreateDTO, String.class);
  }
}

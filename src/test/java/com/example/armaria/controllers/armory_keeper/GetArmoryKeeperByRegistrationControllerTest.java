package com.example.armaria.controllers.armory_keeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetArmoryKeeperByRegistrationControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;

    ResponseEntity<ArmoryKepper> response = restTemplate.getForEntity(baseUrl, null, ArmoryKepper.class);
    assertNull(response.getBody());
  }

  @Test
  void test_createArmoryKeeper_before() {
    getArmoryKeeper();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;

    ResponseEntity<ArmoryKepper> response = restTemplate.getForEntity(baseUrl, ArmoryKepper.class);

    ArmoryKepper armoryKeeper = response.getBody();

    assertNotNull(armoryKeeper);
    assertEquals(registration, armoryKeeper.getRegistrationNumber());
  }

  private void getArmoryKeeper() {
    String createArmoryKeeperUrl = "http://localhost:" + port + "/api/v1/armory_keepers";

    ArmoryKeeperCreateDTO armoryKeeperCreateDTO = new ArmoryKeeperCreateDTO(
        "any-registration",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(createArmoryKeeperUrl, armoryKeeperCreateDTO, String.class);
  }
}

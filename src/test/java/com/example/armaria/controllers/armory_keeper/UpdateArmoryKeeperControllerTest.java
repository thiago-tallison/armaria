package com.example.armaria.controllers.armory_keeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;
import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperUpdateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UpdateArmoryKeeperControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
  }

  @Test
  void testHandle() {
    createArmoryKeeper();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;

    ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO = new ArmoryKeeperUpdateDTO("nome", "email",
        "telefone");

    HttpEntity<ArmoryKeeperUpdateDTO> requestEntity = getRequestBody(armoryKeeperUpdateDTO);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void test_handle_deve_retornar_not_found_para_matricula_nao_encontrada() {
    String registration = "matricula-nao-existente";
    String baseUrl = "http://localhost:" + port + "/api/v1/armory_keepers/" + registration;

    ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO = new ArmoryKeeperUpdateDTO("nome", "email",
        "telefone");

    HttpEntity<ArmoryKeeperUpdateDTO> requestEntity = getRequestBody(armoryKeeperUpdateDTO);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  private void createArmoryKeeper() {
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

  public HttpEntity<ArmoryKeeperUpdateDTO> getRequestBody(ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO) {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<ArmoryKeeperUpdateDTO> requestEntity = new HttpEntity<>(armoryKeeperUpdateDTO, headers);
    return requestEntity;
  }
}

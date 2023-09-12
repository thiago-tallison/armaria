package com.example.armaria.controllers.armorer;

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

import com.example.armaria.use_cases.armorer.ArmorerCreateDTO;
import com.example.armaria.use_cases.armorer.ArmorerUpdateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UpdateArmorerControllerTest {
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
    createArmorer();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers/" + registration;

    ArmorerUpdateDTO armorerUpdateDTO = new ArmorerUpdateDTO("nome", "email",
        "telefone");

    HttpEntity<ArmorerUpdateDTO> requestEntity = getRequestBody(armorerUpdateDTO);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void test_handle_deve_retornar_not_found_para_matricula_nao_encontrada() {
    String registration = "matricula-nao-existente";
    String baseUrl = "http://localhost:" + port + "/api/v1/armorers/" + registration;

    ArmorerUpdateDTO armorerUpdateDTO = new ArmorerUpdateDTO("nome", "email",
        "telefone");

    HttpEntity<ArmorerUpdateDTO> requestEntity = getRequestBody(armorerUpdateDTO);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  private void createArmorer() {
    String createArmorerUrl = "http://localhost:" + port + "/api/v1/armorers";

    ArmorerCreateDTO armorerCreateDTO = new ArmorerCreateDTO(
        "any-registration",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(createArmorerUrl, armorerCreateDTO, String.class);
  }

  public HttpEntity<ArmorerUpdateDTO> getRequestBody(ArmorerUpdateDTO armorerUpdateDTO) {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<ArmorerUpdateDTO> requestEntity = new HttpEntity<>(armorerUpdateDTO, headers);
    return requestEntity;
  }
}

package com.example.armaria.controllers.municipal_guard;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateMunicipalGuardControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());
  }

  @Test
  void handle() {
    getMunicipalGuard();

    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/" + registration;

    MunicipalGuardCreateDTO gmDto = new MunicipalGuardCreateDTO(registration, "nome", "email",
        "telefone");

    HttpEntity<MunicipalGuardCreateDTO> requestEntity = getRequestBody(gmDto);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void test_handle_deve_retornar_not_found_para_matricula_nao_encontrada() {
    String registration = "matricula-nao-existente";
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/" + registration;

    MunicipalGuardCreateDTO gmDto = new MunicipalGuardCreateDTO(registration, "nome", "email",
        "telefone");

    HttpEntity<MunicipalGuardCreateDTO> requestEntity = getRequestBody(gmDto);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  public void getMunicipalGuard() {
    String registration = "any-registration";
    String baseUrl = "http://localhost:" + port + "/api/v1/municipal_guards/create";

    MunicipalGuardCreateDTO municipalGuardCreateDTO = new MunicipalGuardCreateDTO(
        registration,
        "João da Silva",
        "joao@example.com",
        "1234567890");

    restTemplate.postForEntity(baseUrl, municipalGuardCreateDTO, String.class);
  }

  public HttpEntity<MunicipalGuardCreateDTO> getRequestBody(MunicipalGuardCreateDTO gmDto) {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<MunicipalGuardCreateDTO> requestEntity = new HttpEntity<>(gmDto, headers);
    return requestEntity;
  }
}

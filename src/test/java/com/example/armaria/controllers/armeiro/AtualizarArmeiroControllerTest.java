package com.example.armaria.controllers.armeiro;

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

import com.example.armaria.use_cases.armeiro.AtualizarArmeiroDTO;
import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AtualizarArmeiroControllerTest {
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
    criarArmeiro();

    String matricula = "qualquer-matricula";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;

    AtualizarArmeiroDTO armeiroDto = new AtualizarArmeiroDTO("nome", "email",
        "telefone");

    HttpEntity<AtualizarArmeiroDTO> requestEntity = getRequestBody(armeiroDto);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void test_handle_deve_retornar_not_found_para_matricula_nao_encontrada() {
    String matricula = "matricula-nao-existente";
    String baseUrl = "http://localhost:" + port + "/api/armeiro/" + matricula;

    AtualizarArmeiroDTO armeiroDto = new AtualizarArmeiroDTO("nome", "email",
        "telefone");

    HttpEntity<AtualizarArmeiroDTO> requestEntity = getRequestBody(armeiroDto);

    ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, Void.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  private void criarArmeiro() {
    String criarArmeiroUrl = "http://localhost:" + port + "/api/armeiro";

    CriarArmeiroDTO armeiroDTO = new CriarArmeiroDTO(
        "qualquer-matricula",
        "João da Silva",
        "joao@example.com",
        "1234567890",
        "login",
        "senha123");

    restTemplate.postForEntity(criarArmeiroUrl, armeiroDTO, String.class);
  }

  public HttpEntity<AtualizarArmeiroDTO> getRequestBody(AtualizarArmeiroDTO armeiroDto) {
    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<AtualizarArmeiroDTO> requestEntity = new HttpEntity<>(armeiroDto, headers);
    return requestEntity;
  }
}

package com.example.armaria.controllers.equipamento;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletarEquipamentoControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void deve_ser_possivel_deletar_um_equipamento() {
    String numSerieEquipamento = "123456";
    String baseUrl = "http://localhost:" + port + "/api/equipamento/" + numSerieEquipamento;

    ciarEquipamento();

    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> requestBody = new HttpEntity<>(numSerieEquipamento, headers);

    ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestBody, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  public void ciarEquipamento() {
    String baseUrl = "http://localhost:" + port + "/api/equipamento";

    CriarEquipamentoComItemDTO equipamentoComItemDto = new CriarEquipamentoComItemDTO("Equipamento 1",
        "123456", true, 10);

    restTemplate.postForEntity(baseUrl, equipamentoComItemDto, Void.class);
  }
}

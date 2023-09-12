package com.example.armaria.presenter.rest.api.equipament;

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
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.core.usecases.equipament.EquipamentCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeleteEquipamentControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void itShouldBeAbleToDeleteAEquipament() {
    String equipamentSerialNumber = "123456";
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments/" + equipamentSerialNumber;

    createEquipament();

    restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> requestBody = new HttpEntity<>(equipamentSerialNumber, headers);

    ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, requestBody, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  public void createEquipament() {
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments";

    EquipamentCreateDTO equipamentCreateDTO = new EquipamentCreateDTO("Equipament 1",
        "123456", true, 10);

    restTemplate.postForEntity(baseUrl, equipamentCreateDTO, Void.class);
  }
}

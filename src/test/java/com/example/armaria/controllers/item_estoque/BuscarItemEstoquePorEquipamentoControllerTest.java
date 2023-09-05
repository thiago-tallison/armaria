package com.example.armaria.controllers.item_estoque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BuscarItemEstoquePorEquipamentoControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String numSerie = "qualquer-num-serie";

    criarItemEstoque(numSerie);

    String baseUrl = "http://localhost:" + port + "/api/item-estoque/num-serie/" + numSerie;

    ResponseEntity<ItemEstoque> response = restTemplate.getForEntity(baseUrl, ItemEstoque.class);

    ItemEstoque itemEstoque = response.getBody();

    assertNotNull(itemEstoque);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(numSerie, itemEstoque.getEquipamento().getNumSerie());
  }

  public void criarItemEstoque(String numSerie) {
    String baseUrl = "http://localhost:" + port + "/api/equipamento";

    CriarEquipamentoComItemDTO equipamentoComItemDto = new CriarEquipamentoComItemDTO("Nome do equipamento",
        numSerie, true, 10);

    restTemplate.postForEntity(baseUrl, equipamentoComItemDto, Void.class);
  }
}

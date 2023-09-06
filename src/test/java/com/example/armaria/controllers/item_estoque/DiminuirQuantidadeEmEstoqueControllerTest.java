package com.example.armaria.controllers.item_estoque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DiminuirQuantidadeEmEstoqueControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    criarItemEstoque();

    long itemEstoqueId = 1L;
    String baseUrl = "http://localhost:" + port + "/api/item-estoque/" + itemEstoqueId + "/diminuir";
    String baseUrlGet = "http://localhost:" + port + "/api/item-estoque/" + itemEstoqueId;

    DiminuirQuantidadeEmEstoqueRequest quantidade = new DiminuirQuantidadeEmEstoqueRequest(5);

    ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, quantidade, Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    ResponseEntity<ItemEstoque> response1 = restTemplate.getForEntity(baseUrlGet, ItemEstoque.class);

    ItemEstoque item = response1.getBody();

    assertNotNull(item);
    assertEquals(5, item.getQuantidadeEmEstoque());
  }

  public void criarItemEstoque() {
    String baseUrl = "http://localhost:" + port + "/api/equipamento";

    String nomeEquipamento = UUID.randomUUID().toString();
    String numSerieEquipamento = UUID.randomUUID().toString();

    CriarEquipamentoComItemDTO equipamentoComItemDto = new CriarEquipamentoComItemDTO(nomeEquipamento,
        numSerieEquipamento, true, 10);

    restTemplate.postForEntity(baseUrl, equipamentoComItemDto, Void.class);
  }
}

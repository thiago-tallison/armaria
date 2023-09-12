package com.example.armaria.controllers.stock_item;

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

import com.example.armaria.entities.StockItem;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

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
    String baseUrl = "http://localhost:" + port + "/api/v1/stock_items/" + itemEstoqueId + "/decrease_quantity";
    String baseUrlGet = "http://localhost:" + port + "/api/v1/stock_items/" + itemEstoqueId;

    DiminuirQuantidadeEmEstoqueRequest quantidade = new DiminuirQuantidadeEmEstoqueRequest(5);

    ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, quantidade, Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    ResponseEntity<StockItem> response1 = restTemplate.getForEntity(baseUrlGet, StockItem.class);

    StockItem item = response1.getBody();

    assertNotNull(item);
    assertEquals(5, item.getQuantityInStock());
  }

  public void criarItemEstoque() {
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments";

    String equipamentName = UUID.randomUUID().toString();
    String equipamentSerialNumber = UUID.randomUUID().toString();

    EquipamentCreateDTO equipamentCreateDTO = new EquipamentCreateDTO(equipamentName,
        equipamentSerialNumber, true, 10);

    restTemplate.postForEntity(baseUrl, equipamentCreateDTO, Void.class);
  }
}

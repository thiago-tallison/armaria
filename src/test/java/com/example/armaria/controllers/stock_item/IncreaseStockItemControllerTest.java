package com.example.armaria.controllers.stock_item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.dtos.stock_item.IncreaseStockItemQuantityDTO;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IncreaseStockItemControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    criarItemEstoque();

    long itemEstoqueId = 1L;
    String baseUrl = "http://localhost:" + port + "/api/v1/stock_items/" + itemEstoqueId + "/increase_quantity";

    IncreaseStockItemQuantityDTO quantidade = new IncreaseStockItemQuantityDTO(10);

    ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl, quantidade, Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testHandle_deve_falhar_para_quantidade_menor_ou_igual_a_zero() {
    criarItemEstoque();

    long itemEstoqueId = 1L;
    String baseUrl = "http://localhost:" + port + "/api/v1/stock_items/" + itemEstoqueId + "/increase_quantity";

    IncreaseStockItemQuantityDTO quantidadeZero = new IncreaseStockItemQuantityDTO(0);
    IncreaseStockItemQuantityDTO quantidadeMenorQueZero = new IncreaseStockItemQuantityDTO(-1);

    assertEquals(HttpStatus.BAD_REQUEST,
        restTemplate.postForEntity(baseUrl, quantidadeZero, Void.class).getStatusCode());

    assertEquals(HttpStatus.BAD_REQUEST,
        restTemplate.postForEntity(baseUrl, quantidadeMenorQueZero, Void.class).getStatusCode());
  }

  public void criarItemEstoque() {
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments";

    EquipamentCreateDTO equipamentCreateDTO = new EquipamentCreateDTO("Equipament A",
        "Numero de série", true, 10);

    restTemplate.postForEntity(baseUrl, equipamentCreateDTO, Void.class);
  }
}

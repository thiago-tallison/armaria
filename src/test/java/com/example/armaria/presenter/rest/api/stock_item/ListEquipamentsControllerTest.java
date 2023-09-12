package com.example.armaria.presenter.rest.api.stock_item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.armaria.core.domain.StockItem;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ListEquipamentsControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testHandle() {
    String serialNumber = "qualquer-num-serie";

    criarItemEstoque(serialNumber);

    String baseUrl = "http://localhost:" + port + "/api/v1/stock_items/serial_number/" + serialNumber;

    ResponseEntity<StockItem> response = restTemplate.getForEntity(baseUrl, StockItem.class);

    StockItem stockItem = response.getBody();

    assertNotNull(stockItem);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(serialNumber, stockItem.getEquipament().getSerialNumber());
  }

  public void criarItemEstoque(String serialNumber) {
    String baseUrl = "http://localhost:" + port + "/api/v1/equipaments";

    EquipamentCreateDTO equipamentCreateDTO = new EquipamentCreateDTO("Equipament A",
        serialNumber, true, 10);

    restTemplate.postForEntity(baseUrl, equipamentCreateDTO, Void.class);
  }
}
